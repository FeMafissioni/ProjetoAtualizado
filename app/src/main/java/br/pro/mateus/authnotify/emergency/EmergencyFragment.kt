package br.pro.mateus.authnotify.emergency

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.pro.mateus.authnotify.CustomResponse
import br.pro.mateus.authnotify.R
import br.pro.mateus.authnotify.databinding.EmergencyFragmentBinding
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder

class EmergencyFragment : Fragment() {
    private var _binding: EmergencyFragmentBinding? = null
    private val binding get() = _binding!!

    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()

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
    ): View {
        _binding = EmergencyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name")
        val phone = arguments?.getString("phone")

        binding.tvName.text = name

        Glide.with(this)
            .load(arguments?.getString("foto"))
            .into(binding.IVPhoto)

        binding.btnAccept.setOnClickListener {
            mandarResposta(true).addOnCompleteListener(requireActivity()) { result ->
                if (result.result.status == "SUCCESS") {
                    findNavController().navigate(R.id.action_EmergencyFragment_to_TimerFragment)
                }else{
                    Snackbar.make(requireView(),"Deu ruim!",Snackbar.LENGTH_LONG).show()
                }
            }

        }

        binding.btnRefuse.setOnClickListener {
            mandarResposta(false).addOnCompleteListener { result ->
                if(result.result.status == "SUCCESS"){
                    findNavController().navigate(R.id.action_EmergencyFragment_to_ProfileFragment)
                }else{
                    Snackbar.make(requireView(),"Deu ruim!",Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun mandarResposta(status: Boolean): Task<CustomResponse> {
        val functions = Firebase.functions("southamerica-east1")
        val auth = Firebase.auth

        val uid = auth.currentUser!!.uid
        Log.d("EmergencyFragment", uid)

        val resposta = hashMapOf(
            "uid" to uid,
            "Dentistauid" to uid,
            "emergenciaID" to arguments?.getString("id"),
            "status" to status,
        )
        return functions
            .getHttpsCallable("setRespostaDentistas")
            .call(resposta)
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