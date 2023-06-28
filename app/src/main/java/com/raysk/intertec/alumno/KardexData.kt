package com.raysk.intertec.alumno

/** Clase que contine los datos de las calificaciones del kardex del alumno */
data class KardexData(
    var clave: String = "",
    var materia: String = "",
    var calificacion: String = "",
    var periodo: Int = 0,
    val estado: Int = 0,
)
