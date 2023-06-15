package br.pro.mateus.authnotify.emergency

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.pro.mateus.authnotify.R
import br.pro.mateus.authnotify.databinding.FragmentTimerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class TImerFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    private lateinit var countDownTimer: CountDownTimer
    private val totalTime = 90000 // em milissegundos

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        countDownTimer = object : CountDownTimer(totalTime.toLong(), 1_000){
            override fun onTick(remaining: Long) {
                val secondsRemaing = remaining/1000
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                binding.tvTimer.text = secondsRemaing.toString()
                if(uid!=null){
                    val database = FirebaseFirestore.getInstance()
                    val collection = database.collection("ResultadoEmergencias")

                    collection.whereEqualTo("Dentistauid", uid).get()
                        .addOnSuccessListener {
                            for(document in it){
                                collection.document(document.id)
                            }
                        }
                }

            }

            override fun onFinish() {
                binding.tvTimer.text = "Infelizmente não foi dessa vez:/"
                findNavController().navigate(R.id.action_TImerFragment_to_ProfileFragment)
            }
        }
        countDownTimer.start()
    }
}