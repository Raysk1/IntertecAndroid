package com.raysk.intertec.alumno

/** Clase que contine los datos de las calificaciones del alumno */
data class Calificaciones(
    var clave: String = "",
    var materia: String = "",
    var notas: List<Int> = emptyList(),
    var promedio: Float = 0f,
)
