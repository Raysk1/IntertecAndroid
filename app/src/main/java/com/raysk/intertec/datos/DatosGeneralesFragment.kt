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

class DatosGeneralesFragment : Fragment() {
    private lateinit var tvNombre: TextView
    private lateinit var tvNoControl: TextView
    private lateinit var tvCurp: TextView
    private lateinit var tvCarrera: TextView
    private lateinit var tvEspecialidad: TextView
    private lateinit var tvModalidad: TextView
    private lateinit var tvPlan: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datos_generales, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvNombre = view.findViewById(R.id.tvNombre)
        tvNoControl = view.findViewById(R.id.tvNoControl)
        tvCurp = view.findViewById(R.id.tvCurp)
        tvCarrera = view.findViewById(R.id.tvCarrera)
        tvEspecialidad = view.findViewById(R.id.tvEspecialidad)
        tvModalidad = view.findViewById(R.id.tvModalidad)
        tvPlan = view.findViewById(R.id.tvPlan)
        val alumno = alumno
        tvNombre.text = alumno!!.datosGenerales.nombre
        tvNoControl.text = alumno.control
        tvCurp.text = alumno.datosGenerales.curp
        tvCarrera.text = alumno.datosGenerales.carrera
        tvEspecialidad.text = alumno.datosGenerales.especialidad
        tvModalidad.text = "matutina"
        tvPlan.text = alumno.datosGenerales.planDeEstudios
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }
}