package com.raysk.intertec.calificaciones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno


class CalificacionesFragment : Fragment() {

    var parcialActual = 0
    var promedioTotal = 0f
    val alumno = Alumno.alumno!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (alumno.calificaciones.size > 0) {
            return inflater.inflate(R.layout.fragment_calificaciones, container, false)
        } else {
            return inflater.inflate(R.layout.fragment_no_content, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (alumno.calificaciones.size > 0) {
            promedioTotal = alumno.promedioDelSemestreActual
            parcialActual = alumno.parcialActual
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.adapter = CalificacionesAdapter(alumno.calificaciones)
            recyclerView.layoutManager = LinearLayoutManager(view.context)
        }
    }
}