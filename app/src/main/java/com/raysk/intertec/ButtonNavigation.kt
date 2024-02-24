package com.raysk.intertec

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.raysk.intertec.util.update.Updates

class ButtonNavigation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_button_navigation)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.datosGeneralesFragment)
                .build();*/
        val navController =
            findNavController(this, R.id.nav_host_fragment_activity_button_navigation)
        //NavigationUI.setupActionBarWithNavController(this, navController);
        setupWithNavController(navView, navController)

        val updates = Updates(this)
        updates.buscar()
    }
}