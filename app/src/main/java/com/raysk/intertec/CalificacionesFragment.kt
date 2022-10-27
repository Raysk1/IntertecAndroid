package com.raysk.intertec

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.alumno.Calificaciones
import com.raysk.intertec.views.ItemView


class CalificacionesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calificaciones, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alumno = Alumno.getAlumno()
        val layout = view.findViewById<LinearLayout>(R.id.lyContenedor)
        for (calificacion: Calificaciones in alumno.calificaciones) {
            val itemView = activity?.let { ItemView(it.applicationContext) }
            val promedio = if (calificacion.promedio.isNaN()) "SN" else calificacion.promedio.toInt().toString()
            itemView?.setData(calificacion.materia,promedio)
            layout.addView(itemView)
        }
    }
}