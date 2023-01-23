package com.raysk.intertec.notas

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class AdaptadorDeSecciones(
    fm: FragmentManager?,
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentStatePagerAdapter(
    fm!!, behavior
) {
    private val fragmentos: MutableList<Fragment> = ArrayList()
    private val titulosFragmentos: MutableList<String> = ArrayList()
    override fun getItem(position: Int): Fragment {
        return fragmentos[position]
    }

    override fun getCount(): Int {
        return fragmentos.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentos.add(fragment)
        titulosFragmentos.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titulosFragmentos[position]
    }
}