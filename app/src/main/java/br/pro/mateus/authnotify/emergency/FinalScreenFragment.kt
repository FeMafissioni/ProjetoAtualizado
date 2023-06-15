package br.pro.mateus.authnotify.emergency

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.pro.mateus.authnotify.R
import br.pro.mateus.authnotify.databinding.FragmentFinalScreenBinding
import br.pro.mateus.authnotify.databinding.FragmentTimerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FinalScreenFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private var _binding: FragmentFinalScreenBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(name: String?, phone: String?, photo: String?, id: String?): EmergencyFragment {
            val fragment = EmergencyFragment()
            val args = Bundle()
            args.putString("name", name)
            args.putString("phone", phone)
            args.putString("foto", photo)
            args.putString("id", id)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinalScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name")
        val phone = arguments?.getString("phone")

        binding.tvPacientName.text = name
        binding.tvPacientNumber.text = phone

        binding.btnSendLoc.setOnClickListener {
            //logica por tras do btn
            //pegar e mandar lag e long para o cliente
        }

        binding.btnReceiveLoc.setOnClickListener {
            //logica por tras do btn
            //pegara lag e long do firebase e jogar no mapa
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}