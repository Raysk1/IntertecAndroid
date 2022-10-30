package com.raysk.intertec

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.raysk.intertec.alumno.Calificaciones
import com.raysk.intertec.alumno.Kardex
import com.raysk.intertec.alumno.Kardex.Companion.CURSADO
import com.raysk.intertec.alumno.Kardex.Companion.POR_CURSAR
import com.raysk.intertec.alumno.KardexData

class ModalFragment() : DialogFragment() {

    val CALIFICACIONES = 1
    val KARDEX = 2
    private lateinit var calificacion: Calificaciones
    private lateinit var kardexData: KardexData
    private var title: String? = null
    var seleccion = 0

    constructor(title: String, calificacion: Calificaciones) : this() {
        this.title = title
        this.calificacion = calificacion
        seleccion = CALIFICACIONES
    }

    constructor(title: String, kardexData: KardexData) : this() {
        this.title = title
        this.kardexData = kardexData
        seleccion = KARDEX
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return createModal()
    }

    private fun createModal(): Dialog {
        val buider = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.fragment_modal, null)
        buider.setView(view)

        if (view != null) {
            val tvMateria = view.findViewById<TextView>(R.id.materia)
            val tvClave = view.findViewById<TextView>(R.id.clave)
            val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            var materia = ""
            var clave = ""
            val modalLayout = view.findViewById<LinearLayout>(R.id.modalLayout)

            when (seleccion) {
                CALIFICACIONES -> {
                    materia = calificacion.materia
                    clave = calificacion.clave
                    createCalificacionesLayout(tvMateria, modalLayout)
                }
                KARDEX -> {
                    materia = kardexData.materia
                    clave = kardexData.clave
                    createKardexLayout(tvMateria, modalLayout)
                }
            }


            tvMateria.text = materia
            tvClave.text = clave
            tvTitle.text = title
        }

        return buider.create()
    }

    private fun createTitulo(tvReferencia: TextView, texto: String): TextView {
        val titulo = TextView(activity)
        titulo.text = texto
        titulo.setTextColor(Color.BLACK)
        titulo.textSize = 18F
        titulo.isAllCaps = true
        titulo.setTypeface(null, Typeface.BOLD)
        titulo.gravity = tvReferencia.gravity
        titulo.setPadding(
            tvReferencia.paddingLeft,
            tvReferencia.paddingTop,
            tvReferencia.paddingRight,
            tvReferencia.paddingBottom
        )
        return titulo
    }

    private fun createContenido(tvReferencia: TextView, texto: String): TextView {
        val contenido = TextView(activity)
        contenido.text = texto
        contenido.setTextColor(Color.BLACK)
        contenido.textSize = 16F
        contenido.gravity = tvReferencia.gravity
        contenido.setPadding(
            tvReferencia.paddingLeft,
            tvReferencia.paddingTop,
            tvReferencia.paddingRight,
            tvReferencia.paddingBottom
        )
        return contenido
    }

    private fun createCalificacionesLayout(tvReferencia: TextView, layout: LinearLayout) {
        calificacion.notas.forEachIndexed { index: Int, nota: Int ->
            layout.addView(createTitulo(tvReferencia, "Parcial " + (index + 1) + ":"))
            layout.addView(createContenido(tvReferencia, nota.toString()))
        }
    }

    private fun createKardexLayout(tvReferencia: TextView, layout: LinearLayout) {
        layout.addView(createTitulo(tvReferencia, "Calificacion"))
        layout.addView(
            createContenido(
                tvReferencia,
                if (kardexData.calificacion.isEmpty()) "Sin calificacion" else kardexData.calificacion
            )
        )
        layout.addView(createTitulo(tvReferencia, "Periodo"))
        layout.addView(createContenido(tvReferencia, kardexData.periodo.toString()))
        layout.addView(createTitulo(tvReferencia, "Estado"))
        val estado = when (kardexData.estado) {
            CURSADO -> "Cursado"
            POR_CURSAR -> "Por cursar"
            Kardex.EN_CURSO -> "En curso"
            else -> ""
        }
        layout.addView(createContenido(tvReferencia, estado))
    }
}