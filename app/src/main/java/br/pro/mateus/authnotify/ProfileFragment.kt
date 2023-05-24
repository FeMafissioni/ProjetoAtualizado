package br.pro.mateus.authnotify

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.pro.mateus.authnotify.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val db = Firebase.firestore

    companion object {
        val TAG = "profile_fragment"
    }

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

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {
            val database = FirebaseFirestore.getInstance()
            val collection = database.collection("users")

            collection.whereEqualTo("uid", uid).get()
                .addOnSuccessListener {
                    for (values in it) {
                        val data = values.data

                        val name = data.get("nome").toString()
                        val email = data.get("email").toString()
                        val endereco1 = data.get("endereco1").toString()
                        val endereco2 = data.get("endereco2").toString()
                        val endereco3 = data.get("endereco3").toString()
                        val curriculo = data.get("curriculo").toString()

                        binding.tvName.text = name
                        binding.tvEmail.text = email
                        binding.tvEnd1.text = endereco1
                        binding.tvEnd2.text = endereco2
                        binding.tvEnd3.text = endereco3
                        binding.tvCurriculo.text = curriculo
                    }
                }
        }

    }
}
