package com.raysk.intertec.datos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno.Companion.alumno

class DatosPersonalesFragment : Fragment() {
    private lateinit var tvCiudad: TextView
    private lateinit var tvColonia: TextView
    private lateinit var tvCalle: TextView
    private lateinit var tvNoCalle: TextView
    private lateinit var tvCp: TextView
    private lateinit var tvTelefono: TextView
    private lateinit var tvCorreoPer: TextView
    private lateinit var tvCorreoIns: TextView
    private lateinit var tvFechaNac: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datos_personales, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCiudad = view.findViewById(R.id.tvCiudad)
        tvColonia = view.findViewById(R.id.tvColonia)
        tvCalle = view.findViewById(R.id.tvCalle)
        tvNoCalle = view.findViewById(R.id.tvNoCalle)
        tvCp = view.findViewById(R.id.tvCp)
        tvTelefono = view.findViewById(R.id.tvTelefono)
        tvCorreoPer = view.findViewById(R.id.tvCorreoPersonal)
        tvCorreoIns = view.findViewById(R.id.tvCorreoInstitucional)
        tvFechaNac = view.findViewById(R.id.tvFechaNac)
        val alumno = alumno
        tvCiudad.text = alumno!!.datosPersonales.ciudad
        tvColonia.text = alumno.datosPersonales.colonia
        tvCalle.text = alumno.datosPersonales.calle
        tvNoCalle.text = alumno.datosPersonales.noCalle
        tvCp.text = alumno.datosPersonales.cp
        tvTelefono.text = alumno.datosPersonales.telefono
        tvCorreoPer.text = alumno.datosPersonales.correoPersonal
        tvCorreoIns.text = alumno.datosPersonales.correoInstitucional
        tvFechaNac.text = alumno.datosPersonales.fechaDeNacimiento
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }
}