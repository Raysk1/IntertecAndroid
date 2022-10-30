package com.raysk.intertec.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.raysk.intertec.R

class ItemView @JvmOverloads constructor (
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), View.OnClickListener{
    private val materia: TextView
    private val calificacion: TextView
    private val view: View
    private var listener: OnClickListener? = null
    init {
        view = LayoutInflater.from(context).inflate(R.layout.view_item,this,true)
        materia = view.findViewById(R.id.tvMateria)
        calificacion = view.findViewById(R.id.tvCalificacion)
        setOnClickListener(this)
    }

    fun setData(materia:String, calificacion: String){
        this.materia.setText(materia)
        this.calificacion.setText(calificacion)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP) {
            if (listener != null) listener?.onClick(this)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        this.listener = listener
    }

    override fun onClick(v: View?) {

    }
}