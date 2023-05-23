package br.pro.mateus.authnotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.pro.mateus.authnotify.databinding.FragmentProfileBinding
import br.pro.mateus.authnotify.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth



    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val uid = auth.currentUser?.uid
        if(uid != null){
            ShowUserProfile(uid)
        }

    }

    private fun ShowUserProfile(uid: String){
        val userRef = db.collection("users").document(uid)
        userRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()){
                val name = documentSnapshot.getString("nome")
                val email = documentSnapshot.getString("email")

                binding.tvUserName.text = name
                binding.tvEmail.text = email
            }
        }
    }

}



