package com.raysk.intertec.views

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Calificaciones
import com.raysk.intertec.alumno.HorarioEvent
import com.raysk.intertec.alumno.Kardex.Companion.CURSADO
import com.raysk.intertec.alumno.Kardex.Companion.EN_CURSO
import com.raysk.intertec.alumno.Kardex.Companion.POR_CURSAR
import com.raysk.intertec.alumno.Kardex.Companion.REPITE
import com.raysk.intertec.alumno.Kardex.Companion.REPROBADO
import com.raysk.intertec.alumno.KardexData

class ModalFragment() : DialogFragment() {

    private val CALIFICACIONES = 1
    private val KARDEX = 2
    private val HORARIO = 3
    private lateinit var calificacion: Calificaciones
    private lateinit var kardexData: KardexData
    private lateinit var horarioData: HorarioEvent
    private var title: String? = null
    private var seleccion = 0
    private lateinit var tvReferencia: TextView
    private lateinit var layout: LinearLayout

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

    constructor(title: String, horarioData: HorarioEvent) : this() {
        this.title = title
        this.horarioData = horarioData
        seleccion = HORARIO
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return createModal()
    }

    /** Crea el Modal */
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
            layout = view.findViewById(R.id.modalLayout)
            tvReferencia = tvMateria

            //comprueba la seleccion
            when (seleccion) {
                CALIFICACIONES -> {
                    materia = calificacion.materia
                    clave = calificacion.clave
                    createCalificacionesLayout()
                }

                KARDEX -> {
                    materia = kardexData.materia
                    clave = kardexData.clave
                    createKardexLayout()
                }

                HORARIO -> {
                    materia = horarioData.materia
                    clave = horarioData.clave
                    createHorarioLayout()
                }
            }


            tvMateria.text = materia
            tvClave.text = clave
            tvTitle.text = title
        }

        return buider.create()
    }

    /**crea un textView de titulo(en negritas)
     * @param texto Texto del titulo*/
    private fun createTitulo(texto: String): TextView {
        val titulo = TextView(activity)
        titulo.text = texto
        titulo.setTextColor(Color.BLACK)

        titulo.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            requireContext().resources.getDimension(R.dimen.x9sp)
        )
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

    /**crea un textView de contenido
     * @param texto Texto del contenido*/
    private fun createContenido(texto: String): TextView {
        val contenido = TextView(activity)
        contenido.text = texto
        contenido.setTextColor(Color.BLACK)
        contenido.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            requireContext().resources.getDimension(R.dimen.x8sp)
        )
        contenido.gravity = tvReferencia.gravity
        contenido.setPadding(
            tvReferencia.paddingLeft,
            tvReferencia.paddingTop,
            tvReferencia.paddingRight,
            tvReferencia.paddingBottom
        )
        return contenido
    }

    /** Crea el Layout del modal de calificaciones*/
    private fun createCalificacionesLayout() {
        calificacion.notas.forEachIndexed { index: Int, nota: Int ->
            layout.addView(createTitulo("Parcial " + (index + 1) + ":"))
            layout.addView(createContenido(nota.toString()))
        }
    }

    /** Crea el Layout del modal del kardex*/
    private fun createKardexLayout() {
        layout.addView(createTitulo("Calificacion:"))
        layout.addView(
            createContenido(
                kardexData.calificacion.ifEmpty { "Sin calificacion" }
            )
        )
        layout.addView(createTitulo("Periodo:"))
        layout.addView(createContenido(kardexData.periodo.toString()))
        layout.addView(createTitulo("Estado:"))
        val estado = when (kardexData.estado) {
            CURSADO -> "Cursado"
            POR_CURSAR -> "Por cursar"
            EN_CURSO -> "En curso"
            REPROBADO -> "Reprobado"
            REPITE -> "Repite"
            else -> ""
        }
        layout.addView(createContenido(estado))
    }

    /** Crea el Layout del modal del horario*/
    private fun createHorarioLayout() {
        layout.addView(createTitulo("aula:"))
        layout.addView(createContenido(horarioData.aula))
        layout.addView(createTitulo("Hora de entrada:"))
        layout.addView(createContenido(horarioData.horaDeEntrada.toString() + ":00"))
        layout.addView(createTitulo("Hora de salida:"))
        layout.addView(createContenido(horarioData.horaDeSalida.toString() + ":00"))
    }
}