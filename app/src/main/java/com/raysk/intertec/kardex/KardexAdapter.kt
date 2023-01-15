package com.raysk.intertec.kardex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raysk.intertec.R
import com.raysk.intertec.alumno.KardexData
import com.raysk.intertec.views.CalificacionItemView

class KardexAdapter(private val kardexDataList: List<KardexData>) :
    RecyclerView.Adapter<CalificacionItemView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalificacionItemView {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CalificacionItemView(
            layoutInflater.inflate(
                R.layout.view_item_calificaciones,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CalificacionItemView, position: Int) {
        val item = kardexDataList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = kardexDataList.size
}