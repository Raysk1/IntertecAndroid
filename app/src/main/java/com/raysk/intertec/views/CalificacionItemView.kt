package com.raysk.intertec.views

import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.raysk.intertec.ModalFragment
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Calificaciones
import com.raysk.intertec.alumno.KardexData

/** Elemento Utilizado para mostrar las calificaciones */
class CalificacionItemView(view: View) : ViewHolder(view) {
    private val materia: TextView = view.findViewById(R.id.tvMateria)
    private val calificacion: TextView = view.findViewById(R.id.tvCalificacion)

    fun render(kardexData: KardexData) {
        materia.text = kardexData.materia
        calificacion.text = kardexData.calificacion
        itemView.setOnClickListener {
            val modal = ModalFragment("Detalles", kardexData)
            val activity = itemView.context as FragmentActivity
            activity.supportFragmentManager.let { it1 -> modal.show(it1, "nosexd") }
        }
    }

    fun render(calificaciones: Calificaciones){
        materia.text = calificaciones.materia
        calificacion.text = if(calificaciones.promedio.isNaN()) "SC" else calificaciones.promedio.toInt().toString()
        itemView.setOnClickListener {
            val modal = ModalFragment("Detalles", calificaciones)
            val activity = itemView.context as FragmentActivity
            activity.supportFragmentManager.let { it1 -> modal.show(it1, "nosexd") }
        }
    }

}