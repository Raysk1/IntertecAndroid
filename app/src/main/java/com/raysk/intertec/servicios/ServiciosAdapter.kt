package com.raysk.intertec.servicios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Servicio
import com.raysk.intertec.views.ServicioItemView

class ServiciosAdapter(private val serviciosList: List<Servicio>) :
    RecyclerView.Adapter<ServicioItemView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioItemView {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ServicioItemView(layoutInflater.inflate(R.layout.view_item_servicio, parent, false))
    }

    override fun onBindViewHolder(holder: ServicioItemView, position: Int) {
        val item = serviciosList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = serviciosList.size

}
