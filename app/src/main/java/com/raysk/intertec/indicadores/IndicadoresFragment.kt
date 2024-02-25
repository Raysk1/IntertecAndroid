package com.raysk.intertec.indicadores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.github.nikartm.button.FitButton
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.notas.calificaciones.CalificacionesFragment
import com.raysk.intertec.notas.kardex.KardexFragment

class IndicadoresFragment : Fragment() {
    private var isShowingPromedio = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_indicadores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //avance en porcentaje
        super.onViewCreated(view, savedInstanceState)
        val tvPorcentajeDeAvance = view.findViewById<TextView>(R.id.tvPorcentajeDeAvance)
        val progressBar = view.findViewById<RoundCornerProgressBar>(R.id.progressBarAvance)
        val button = view.findViewById<FitButton>(R.id.btnCambiar)

        //promedio
        val tvSegundoTitulo = view.findViewById<TextView>(R.id.tvSegundoTitulo)
        val tvSegundoTituloValor = view.findViewById<TextView>(R.id.tvSegundoTituloValor)
        val tvPromedio = view.findViewById<TextView>(R.id.tvPromedio)

        val mlIndicadores: MotionLayout = view.findViewById(R.id.mlIndicadores)
        val mlRotacionButton: MotionLayout = view.findViewById(R.id.mlButton)


        button.setOnClickListener {
            isShowingPromedio = if (!isShowingPromedio) {
                mlIndicadores.transitionToEnd()
                mlRotacionButton.transitionToEnd()
                true
            } else {
                mlIndicadores.transitionToStart()
                mlRotacionButton.transitionToStart()
                false
            }
        }
        var porcentaje = 0.0f
        var promedio = 0f
        var segundoTitulo = ""
        var segundoTituloValor = ""
        //el padre de este es el navHost y el padre del navHost es el que se necesita
        val parent = parentFragment
        if (parent is CalificacionesFragment) {
            porcentaje = parent.parcialActual.toFloat() / 6 * 100
            promedio = parent.promedioTotal
            segundoTitulo = "Parcial:"
            segundoTituloValor = parent.parcialActual.toString()
        } else if (parent is KardexFragment) {
            val alumno = Alumno.alumno!!
            porcentaje = Alumno.alumno?.kardex?.avance!!
            promedio = alumno.kardex.promedio
            segundoTitulo = "Creditos acumulados:"
            segundoTituloValor =
                "${alumno.kardex.creditosObtenidos} de ${alumno.kardex.creditosTotales}"
        }

        tvPromedio.text = String.format("%.2f", promedio)
        tvSegundoTitulo.text = segundoTitulo
        tvSegundoTituloValor.text = segundoTituloValor
        tvPorcentajeDeAvance.text = String.format("%.2f", porcentaje)
        progressBar.setProgress(porcentaje)
    }
}