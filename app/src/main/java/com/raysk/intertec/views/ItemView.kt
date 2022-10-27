package com.raysk.intertec.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import com.raysk.intertec.R

class ItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val materia: TextView
    private val calificacion: TextView
    private val view: View
    init {
        view = LayoutInflater.from(context).inflate(R.layout.view_item,this,true)
        materia = view.findViewById(R.id.tvMateria)
        calificacion = view.findViewById(R.id.tvCalificacion)
    }

    fun setData(materia:String, calificacion: String){
        this.materia.setText(materia)
        this.calificacion.setText(calificacion)
    }


}