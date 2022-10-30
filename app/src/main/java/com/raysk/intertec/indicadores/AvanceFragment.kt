package com.raysk.intertec.indicadores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.github.nikartm.button.FitButton
import com.raysk.intertec.CalificacionesFragment
import com.raysk.intertec.KardexFragment
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno

class AvanceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController(view)
        val tvPorcentajeDeAvance = view.findViewById<TextView>(R.id.tvPorcentajeDeAvance)
        val progressBar = view.findViewById<RoundCornerProgressBar>(R.id.progressBarAvance)
        val button = view.findViewById<FitButton>(R.id.btnCambiar)
        button.setOnClickListener {
            navController.navigate(R.id.action_avanceFragment2_to_promedioFragment)
        }
        var porcentaje = 0.0f
        //el padre de este es el navHost y el padre del navHost es el que se necesita
        val parent = parentFragment?.parentFragment
        if ( parent is CalificacionesFragment) {
            porcentaje = parent.parcialActual.toFloat()/6*100
        }else if (parent is KardexFragment){
            porcentaje = Alumno.alumno?.kardex?.avance!!
        }

        tvPorcentajeDeAvance.text = String.format("%.2f", porcentaje)
        progressBar.progress = porcentaje
    }
}