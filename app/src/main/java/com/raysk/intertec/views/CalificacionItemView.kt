package com.raysk.intertec.views

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.raysk.intertec.ModalFragment
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Calificaciones
import com.raysk.intertec.alumno.Kardex
import com.raysk.intertec.alumno.KardexData
import com.tistory.zladnrms.roundablelayout.RoundableLayout

/** Elemento Utilizado para mostrar las calificaciones */
class CalificacionItemView(view: View) : ViewHolder(view) {
    private val materia: TextView = view.findViewById(R.id.tvMateria)
    private val calificacion: TextView = view.findViewById(R.id.tvCalificacion)
    private val statusColor: RoundableLayout = view.findViewById(R.id.statusColor)

    fun render(kardexData: KardexData) {
        materia.text = kardexData.materia
        calificacion.text = kardexData.calificacion
        statusColor.backgroundColor = when(kardexData.estado){
            Kardex.REPROBADO -> {itemView.resources.getColor(R.color.error)}
            Kardex.EN_CURSO -> {itemView.resources.getColor(R.color.info)}
            Kardex.CURSADO -> {itemView.resources.getColor(R.color.success)}
            Kardex.REPITE -> {itemView.resources.getColor(R.color.warning)}
            else -> {Color.TRANSPARENT}
        }
        itemView.setOnClickListener {
            val modal = ModalFragment("Detalles", kardexData)
            val activity = itemView.context as FragmentActivity
            activity.supportFragmentManager.let { it1 -> modal.show(it1, "nosexd") }
        }
    }

    fun render(calificaciones: Calificaciones){
        materia.text = calificaciones.materia
        calificacion.text = if(calificaciones.promedio.isNaN() || calificaciones.notas.isEmpty()) "SC" else calificaciones.promedio.toInt().toString()
        itemView.setOnClickListener {
            val modal = ModalFragment("Detalles", calificaciones)
            val activity = itemView.context as FragmentActivity
            activity.supportFragmentManager.let { it1 -> modal.show(it1, "nosexd") }
        }
    }

}