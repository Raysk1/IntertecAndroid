package com.raysk.intertec.servicios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Servicio
import com.raysk.intertec.views.ServicioCatalogoItemView

class ServiciosCatalogoAdapter(private val serviciosCatalogoList: List<Servicio>) :
    RecyclerView.Adapter<ServicioCatalogoItemView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioCatalogoItemView {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ServicioCatalogoItemView(
            layoutInflater.inflate(
                R.layout.view_item_catalogo_servicio,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ServicioCatalogoItemView, position: Int) {
        val item = serviciosCatalogoList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = serviciosCatalogoList.size

}