package com.raysk.intertec

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.raysk.intertec.databinding.ActivityButtonNavigationBinding
import com.raysk.intertec.util.update.Updates

class ButtonNavigation : AppCompatActivity() {
    private var binding: ActivityButtonNavigationBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonNavigationBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        //val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.datosGeneralesFragment)
                .build();*/
        val navController =
            findNavController(this, R.id.nav_host_fragment_activity_button_navigation)
        //NavigationUI.setupActionBarWithNavController(this, navController);
        setupWithNavController(binding!!.navView, navController)

        val updates = Updates(this)
        updates.buscar()
    }
}