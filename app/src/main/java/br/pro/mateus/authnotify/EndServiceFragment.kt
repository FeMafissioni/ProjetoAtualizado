package br.pro.mateus.authnotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.pro.mateus.authnotify.databinding.FragmentEndServiceBinding
import br.pro.mateus.authnotify.databinding.FragmentFinalScreenBinding
import br.pro.mateus.authnotify.emergency.EmergencyFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EndServiceFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private var _binding: FragmentEndServiceBinding? = null
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEndServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name")
        val phone = arguments?.getString("phone")

        binding.tvPacientName.text = name
        binding.tvPacientNumber.text = phone

        //mostrar dados do cliente aqui, n sei se puxa da emergencyactivity
        //igual puxou na emergency activity, puxei por via das duvidas

        binding.btnFinish.setOnClickListener {
            //alterar na colecao "DadosSocorristas" para finish e dar navigation para
            //tela profile
            FinalizarEmergencia()
            findNavController().navigate(R.id.action_endfragment_to_profile)
        }
    }

    private fun FinalizarEmergencia(){
        val emergenciaID = arguments?.getString("id")
        val database = FirebaseFirestore.getInstance()
        val collection = database.collection("DadosSocorristas")

        collection.whereEqualTo("emergenciaID", emergenciaID).get()
            .addOnSuccessListener {
                for (document in it){
                    collection.document(document.id)
                        .update("status", "Finish")
                        .addOnSuccessListener {
                            println("Finalizado")
                        }.addOnSuccessListener {
                            println("nao Finalizado")
                        }
                }
            }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}