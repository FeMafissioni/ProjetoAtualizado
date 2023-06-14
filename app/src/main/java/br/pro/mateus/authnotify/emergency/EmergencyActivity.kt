package br.pro.mateus.authnotify.emergency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import br.pro.mateus.authnotify.databinding.EmergencyActivityBinding

class EmergencyActivity : AppCompatActivity() {
    private lateinit var binding: EmergencyActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmergencyActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("nome")
        val phone = intent.getStringExtra("telefone")

        val id = intent.getStringExtra("emergenciaID");

        val fragment = EmergencyFragment.newInstance(name, phone, id)
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(binding.fragmentContainer.id, fragment)
        }
    }
}
