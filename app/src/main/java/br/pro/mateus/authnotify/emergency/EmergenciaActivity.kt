package br.pro.mateus.authnotify.emergency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.pro.mateus.authnotify.R
import br.pro.mateus.authnotify.databinding.ActivityEmergenciaBinding

class EmergenciaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmergenciaBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityEmergenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        navController = findNavController(R.id.nav_host_fragment_content_emergency) // mudar aq
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_emergency)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}