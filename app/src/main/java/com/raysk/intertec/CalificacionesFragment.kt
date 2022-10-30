package com.raysk.intertec

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.alumno.Calificaciones
import com.raysk.intertec.views.ItemView


class CalificacionesFragment : Fragment() {

    var parcialActual = 0
    var promedioTotal: Float = 0f
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calificaciones, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alumno = Alumno.alumno
        val layout = view.findViewById<LinearLayout>(R.id.lyContenedor)
        if (alumno != null) {
            for (calificacion: Calificaciones in alumno.calificaciones) {
                val itemView = activity?.let { ItemView(it.applicationContext) }
                var promedio: String
                var promedioAux = 0f

                if (calificacion.promedio.isNaN()) {
                    promedio = "SC"
                } else {
                    promedioAux = calificacion.promedio
                    promedio = promedioAux.toInt().toString()
                }

                itemView?.setData(calificacion.materia, promedio)
                val param = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
                )

                itemView?.layoutParams = param
                itemView?.setOnClickListener {
                    val modal = ModalFragment("Detalles", calificacion)
                    activity?.supportFragmentManager?.let { it1 -> modal.show(it1, "nosexd") }
                }
                layout.addView(itemView)

                val size = calificacion.notas.size

                if (size > parcialActual) {
                    parcialActual = size
                }
                promedioTotal += promedioAux
            }
        }
        promedioTotal /= alumno?.calificaciones?.size!!
    }
}