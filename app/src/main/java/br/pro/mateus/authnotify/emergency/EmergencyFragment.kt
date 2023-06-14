package br.pro.mateus.authnotify.emergency

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.pro.mateus.authnotify.CustomResponse
import br.pro.mateus.authnotify.MainActivity
import br.pro.mateus.authnotify.ProfileFragment
import br.pro.mateus.authnotify.R
import br.pro.mateus.authnotify.databinding.FragmentEmergencyBinding
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.common.base.Functions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder

class EmergencyFragment : Fragment() {
    //aqui foi inicializado o authentication, as functions, o binding e tambem o gson
    //Aqui n é necessario checar o current user uid
    private lateinit var Auth: FirebaseAuth
    private lateinit var Functions: FirebaseFunctions
    private var _binding: FragmentEmergencyBinding? = null
    private val binding get() = _binding!!
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmergencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //mostrar os dados nas telas
        if((activity as EmergenciaActivity).intent.hasExtra("nome")){
            val intent = (activity as EmergenciaActivity).intent
            binding.tvName.text = intent.getStringExtra("nome")
            Glide.with(this)
                .load(intent.getStringExtra("foto"))
                .into(binding.IVPhoto)
        }

        binding.btnAccept.setOnClickListener {
            setRespostaDentista(true).addOnCompleteListener(requireActivity()){ res->
                if(res.result.status == "SUCCESS"){
                    findNavController().navigate(R.id.action_EmergencyFragment_to_TimerFragment)
                }else{
                    Snackbar.make(requireView(),"Nao foi possivel aceitar", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        binding.btnRefuse.setOnClickListener {
            setRespostaDentista(false).addOnCompleteListener(requireActivity()){ res->
                if (res.result.status == "SUCCESS"){
                    val intent = Intent(binding.root.context, ProfileFragment::class.java)
                    startActivity(intent)
                }else{
                    Snackbar.make(requireView(),"Nao foi possivel recusar", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setRespostaDentista(status: Boolean): Task<CustomResponse> {
        Functions = Firebase.functions("southamerica-east1")
        Auth = Firebase.auth

        val Resposta = hashMapOf(
            "Denstistauid" to Auth.currentUser!!.uid,
            "emergenciaID" to (activity as EmergenciaActivity).intent.getStringExtra("id"),
            "status" to status
        )
        return Functions
            .getHttpsCallable("setRespostaDentistas")
            .call(Resposta)
            .continueWith { task ->
                val result =
                    gson.fromJson((task.result?.data as String), CustomResponse::class.java)
                result
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}