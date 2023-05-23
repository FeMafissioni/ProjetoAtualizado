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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val db = Firebase.firestore

        //initializing firebase authentication
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

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("users").document(uid)
        ref.get().addOnSuccessListener {
            if(it!= null){
                val name = it.data?.get("name").toString()
                val email = it.data?.get("email").toString()
                //val endereco1 = it.data?.get("endereco1").toString()
                //val endereco2 = it.data?.get("endereco2").toString()
                //val endereco3 = it.data?.get("endereco3").toString()
                //val curriculo = it.data?.get("email").toString()

                binding.tvUserName.text = name
                binding.tvEmail.text = email

            }
        }

    }






}



