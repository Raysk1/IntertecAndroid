package com.raysk.intertec

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.views.ItemView


class KardexFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kardex, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = view.findViewById<LinearLayout>(R.id.lyContenedor)
        val alumno = Alumno.alumno

        if (alumno != null) {
            for (data in alumno.kardex.data) {
                val itemView = activity?.let { ItemView(it.applicationContext) }
                itemView?.setData(
                    data.materia,
                    data.calificacion
                )
                itemView?.setOnClickListener {
                    val modal = ModalFragment("Detalles", data)
                    activity?.supportFragmentManager?.let { it1 -> modal.show(it1, "nosexd") }
                }

                layout.addView(itemView)
            }
        }
    }
}