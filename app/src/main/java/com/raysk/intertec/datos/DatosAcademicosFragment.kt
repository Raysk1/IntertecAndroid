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

class DatosAcademicosFragment : Fragment() {
    private lateinit var tvEscuelaProcedencia: TextView
    private lateinit var tvPeriodoIngreso: TextView
    private lateinit var tvPeriodoActual: TextView
    private lateinit var tvPeriodosValidados: TextView
    private lateinit var tvCreditos: TextView
    private lateinit var tvSituacion: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datos_academicos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvEscuelaProcedencia = view.findViewById(R.id.tvEscuelaProcedencia)
        tvCreditos = view.findViewById(R.id.tvCreditos)
        tvPeriodoActual = view.findViewById(R.id.tvPeriodoActual)
        tvPeriodoIngreso = view.findViewById(R.id.tvPeriodoIngreso)
        tvSituacion = view.findViewById(R.id.tvSituacion)
        tvPeriodosValidados = view.findViewById(R.id.tvPeriodosValidados)
        val alumno = alumno
        tvEscuelaProcedencia.text = alumno!!.datosAcademicos.escuelaDeProcedencia
        tvPeriodoIngreso.text = alumno.datosAcademicos.periodoDeIngreso
        tvPeriodosValidados.text = alumno.datosAcademicos.periodosValidados
        tvPeriodoActual.text = alumno.datosAcademicos.periodoActual
        tvCreditos.text = alumno.datosAcademicos.creditosAcumulados
        tvSituacion.text = alumno.datosAcademicos.situacion
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
    }
}