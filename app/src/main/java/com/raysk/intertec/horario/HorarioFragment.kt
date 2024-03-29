package com.raysk.intertec.horario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.jsr310.maxDateAsLocalDate
import com.alamkanak.weekview.jsr310.minDateAsLocalDate
import com.alamkanak.weekview.jsr310.scrollToDateTime
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime


class HorarioFragment : Fragment() {


    private lateinit var weekView: WeekView
    private lateinit var endDay: LocalDate
    private lateinit var startDay: LocalDate
    private val alumno: Alumno = Alumno.alumno!!
    private val adapter = HorarioAdapter()
    private val uiScope = CoroutineScope(Dispatchers.Main)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return if (alumno.horario.size > 0) {
            inflater.inflate(R.layout.fragment_horario, container, false)
        } else {
            inflater.inflate(R.layout.fragment_no_content, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (alumno.horario.size > 0) {
            weekView = view.findViewById(R.id.weekView)
            adapter.fragmentManager = parentFragmentManager
            weekView.adapter = adapter

            uiScope.launch {
                processHorario()
            }

            //Configuarcion del menu de la toolbar
            val toolbar = view.findViewById<Toolbar>(R.id.toolbar2)
            toolbar.inflateMenu(R.menu.horario_menu)
            var currentViewType = WeekViewType.of(weekView.numberOfVisibleDays)
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_today -> {
                        weekView.scrollToDateTime(dateTime = LocalDateTime.now())
                        true
                    }

                    else -> {
                        val viewType = item.mapToWeekViewType()
                        if (viewType != currentViewType) {
                            item.isChecked = !item.isChecked
                            currentViewType = viewType
                            weekView.numberOfVisibleDays = viewType.value
                        }
                        true
                    }
                }
            }
        }
    }


    /** Tipos de opcion de vista del horario */
    private enum class WeekViewType(val value: Int) {
        DayView(1),
        ThreeDayView(3),
        WeekView(5);

        companion object {
            fun of(days: Int): WeekViewType = entries.first { it.value == days }
        }
    }

    private fun MenuItem.mapToWeekViewType(): WeekViewType {
        return when (itemId) {
            R.id.action_day_view -> WeekViewType.DayView
            R.id.action_three_day_view -> WeekViewType.ThreeDayView
            R.id.action_week_view -> WeekViewType.WeekView
            else -> throw IllegalArgumentException("Invalid menu item ID $itemId")
        }
    }

    /**corrutina que procesa el horario en otro hilo */
    private suspend fun processHorario() {
        withContext(Dispatchers.Default) {
            adapter.submitList(alumno.horario)
            val now = LocalDate.now()
            val weekDay = now.dayOfWeek.value
            startDay = now.minusDays((weekDay - 1).toLong())
            endDay = now.plusDays((7 - weekDay).toLong())
        }

        withContext(Dispatchers.Main) {
            weekView.minDateAsLocalDate = startDay
            weekView.maxDateAsLocalDate = endDay
        }
    }

}