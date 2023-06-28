package com.raysk.intertec.horario

import androidx.fragment.app.FragmentManager
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import com.raysk.intertec.alumno.HorarioEvent
import com.raysk.intertec.views.ModalFragment
import java.time.LocalDate
import java.util.Calendar

class HorarioAdapter : WeekView.SimpleAdapter<HorarioEvent>() {
    lateinit var fragmentManager: FragmentManager

    override fun onCreateEntity(item: HorarioEvent): WeekViewEntity {
        val startTime = Calendar.getInstance()
        val endTime = Calendar.getInstance()

        val weekDayNow = LocalDate.now().dayOfWeek.value
        val weekDay = item.dia

        startTime.add(Calendar.DAY_OF_MONTH, weekDay - weekDayNow)
        startTime.set(Calendar.HOUR_OF_DAY, item.horaDeEntrada)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.SECOND, 0)
        startTime.set(Calendar.MILLISECOND, 0)

        endTime.add(Calendar.DAY_OF_MONTH, weekDay - weekDayNow)
        endTime.set(Calendar.HOUR_OF_DAY, item.horaDeSalida)
        endTime.set(Calendar.MINUTE, 0)
        endTime.set(Calendar.SECOND, 0)
        endTime.set(Calendar.MILLISECOND, 0)

        val style = WeekViewEntity.Style.Builder().setBackgroundColor(item.color).build()

        return WeekViewEntity.Event.Builder(item)
            .setId(item.id.toLong())
            .setTitle(item.materia)
            .setSubtitle(item.aula)
            .setStartTime(startTime)
            .setEndTime(endTime)
            .setStyle(style)
            .build()
    }

    override fun onEventClick(data: HorarioEvent) {
        super.onEventClick(data)
        val modal = ModalFragment("Detalles", data)
        modal.show(fragmentManager, "nosexd")
    }
}