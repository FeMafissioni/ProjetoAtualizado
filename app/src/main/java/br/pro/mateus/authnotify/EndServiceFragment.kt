package br.pro.mateus.authnotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.pro.mateus.authnotify.databinding.FragmentEndServiceBinding
import br.pro.mateus.authnotify.databinding.FragmentFinalScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EndServiceFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private var _binding: FragmentEndServiceBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEndServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

}