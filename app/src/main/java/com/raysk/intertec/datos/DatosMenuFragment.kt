package com.raysk.intertec.datos

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno.Companion.alumno

class DatosMenuFragment : Fragment() {
    private lateinit var tvUsername: TextView
    private lateinit var tvUserNumControl: TextView
   private lateinit var tvUsernameIcon: TextView
    private lateinit var btDatosGenerales: Button
    private lateinit var btDatosPersonales: Button
    private lateinit var btDatosAcademicos: Button
    lateinit var tvUserCarrera:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datos_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvUsername = view.findViewById(R.id.userName)
        tvUsernameIcon = view.findViewById(R.id.tvCalificacion)
        tvUserNumControl = view.findViewById(R.id.userNumControl)
        btDatosGenerales = view.findViewById(R.id.btDatosGenerales)
        btDatosPersonales = view.findViewById(R.id.btDatosPersonales)
        btDatosAcademicos = view.findViewById(R.id.btDatosAcademicos)
        tvUserCarrera = view.findViewById(R.id.tvUserCarrera)

        val alumno = alumno
        val navController = findNavController(view)
        val nombre = alumno!!.datosGenerales.nombre
        tvUsername.text = nombre
        val icon = nombre[0].toString() + ""
        tvUsernameIcon.text = icon
        tvUserNumControl.text = alumno.control
        btDatosGenerales.setOnClickListener {
            navController.navigate(
                R.id.action_datosMenuFragment_to_datosGeneralesFragment
            )
        }
        btDatosPersonales.setOnClickListener {
            navController.navigate(
                R.id.action_datosMenuFragment_to_datosPersonalesFragment
            )
        }
        btDatosAcademicos.setOnClickListener {
            navController.navigate(
                R.id.action_datosMenuFragment_to_datosAcademicosFragment
            )
        }
        tvUserCarrera.text = alumno.datosGenerales.carrera
        tvUserCarrera.setBackgroundColor(when(alumno.datosGenerales.carrera){
            "ISC" ->{resources.getColor(R.color.ISCcolor)}
            "IIAL" -> {resources.getColor(R.color.IIALcolor)}
            "INN" -> {resources.getColor(R.color.INNcolor)}
            "IGE" -> {resources.getColor(R.color.IGEcolor)}
            "IIAS" -> {resources.getColor(R.color.IIAScolor)}
            else->{Color.TRANSPARENT}
        })
    }
}