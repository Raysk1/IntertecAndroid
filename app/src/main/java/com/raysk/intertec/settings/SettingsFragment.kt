package com.raysk.intertec.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.raysk.intertec.LoginActivity
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.tutorial.OnBoardingActivity
import com.raysk.intertec.util.preferences.Preferences


class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener {
            Preferences.eliminarDatosJson(view.context.filesDir,Preferences.ALUMNO_FILE_NAME)
            Alumno.alumno = null
            val intent = Intent(view.context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        val navController = findNavController(view)
        val btnCambiarPass: Button = view.findViewById(R.id.btnCambiarPass)
        btnCambiarPass.setOnClickListener {
            navController.navigate(R.id.action_settingsFragment2_to_changePassFragment)
        }

        val btnTutorial = view.findViewById<Button>(R.id.btnTutorial)

        btnTutorial.setOnClickListener{
            val intent = Intent(view.context, OnBoardingActivity::class.java)
            startActivity(intent)
        }


    }
}