package com.raysk.intertec.notas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.raysk.intertec.R
import com.raysk.intertec.notas.calificaciones.CalificacionesFragment
import com.raysk.intertec.notas.kardex.KardexFragment

class NotasFragment : Fragment() {

    private lateinit var tabs: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notas, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabs = view.findViewById(R.id.tabs)
        viewPager = view.findViewById(R.id.viewPager)
        val adaptadorDeSecciones = AdaptadorDeSecciones(childFragmentManager)
        adaptadorDeSecciones.addFragment(KardexFragment(), "Kardex")
        adaptadorDeSecciones.addFragment(CalificacionesFragment(), "Calificaciones")
        viewPager.adapter = adaptadorDeSecciones
        tabs.setupWithViewPager(viewPager)

    }
}