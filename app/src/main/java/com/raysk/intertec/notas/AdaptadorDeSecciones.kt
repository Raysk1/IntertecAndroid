package com.raysk.intertec.notas

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class AdaptadorDeSecciones(fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter(fm, lc) {

    private val fragmentos: MutableList<Fragment> = ArrayList()

    fun addFragment(fragment: Fragment) {
        fragmentos.add(fragment)
    }

    override fun getItemCount(): Int = fragmentos.size

    override fun createFragment(position: Int): Fragment = fragmentos[position]

}