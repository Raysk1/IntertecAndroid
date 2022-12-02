package com.raysk.intertec

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.raysk.intertec.alumno.Alumno

class SettingsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener {
            Alumno.alumno?.eliminarDatosJson(view.context.filesDir)
            val intent = Intent(view.context,LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}