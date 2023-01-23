package com.raysk.intertec.servicios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno

class CatalogoDeServiciosFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogo_de_servicios, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = ServiciosCatalogoAdapter(Alumno.alumno!!.catalogoDeServicios)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar4)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

}