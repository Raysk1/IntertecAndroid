package com.raysk.intertec.indicadores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.github.nikartm.button.FitButton
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.notas.calificaciones.CalificacionesFragment
import com.raysk.intertec.notas.kardex.KardexFragment


class PromedioFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_promedio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)
        val tvSegundoTitulo = view.findViewById<TextView>(R.id.tvSegundoTitulo)
        val tvSegundoTituloValor = view.findViewById<TextView>(R.id.tvSegundoTituloValor)
        val tvPromedio = view.findViewById<TextView>(R.id.tvPromedio)
        val button = view.findViewById<FitButton>(R.id.btnCambiar2)

        button.setOnClickListener {
            navController.navigate(R.id.action_promedioFragment_to_avanceFragment2)
        }
        val parent = parentFragment?.parentFragment
        var promedio = 0f
        var segundoTitulo = ""
        var segundoTituloValor = ""
        if (parent is CalificacionesFragment) {
            promedio = parent.promedioTotal
            segundoTitulo = "Parcial:"
            segundoTituloValor = parent.parcialActual.toString()

        } else if (parent is KardexFragment) {
            val alumno = Alumno.alumno!!
            promedio = alumno.kardex.promedio
            segundoTitulo = "Creditos acumulados:"
            segundoTituloValor =
                "${alumno.kardex.creditosObtenidos} de ${alumno.kardex.creditosTotales}"
        }

        tvPromedio.text = String.format("%.2f", promedio)
        tvSegundoTitulo.text = segundoTitulo
        tvSegundoTituloValor.text = segundoTituloValor
    }
}