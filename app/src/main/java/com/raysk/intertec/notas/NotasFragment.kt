package com.raysk.intertec.notas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.notas.calificaciones.CalificacionesFragment
import com.raysk.intertec.notas.kardex.KardexFragment
import com.raysk.intertec.notas.residencia.ResideciaFragment

class NotasFragment : Fragment() {

    private lateinit var tabs: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_notas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabs = view.findViewById(R.id.tabs)
        viewPager = view.findViewById(R.id.viewPager)
        val adaptadorDeSecciones = AdaptadorDeSecciones(childFragmentManager,lifecycle)
        val nombresTabs = arrayOf("Kardex","Calificaciones","Residencia")
        adaptadorDeSecciones.addFragment(KardexFragment())
        adaptadorDeSecciones.addFragment(CalificacionesFragment())
        if (Alumno.alumno!!.residencia.proyecto.isNotEmpty()){
            adaptadorDeSecciones.addFragment(ResideciaFragment())
        }
        viewPager.adapter = adaptadorDeSecciones

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = nombresTabs[position]
        }.attach()
    }
}