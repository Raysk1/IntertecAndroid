package com.raysk.intertec.alumno

/** Clase que contine los datos generales del alumno */
data class DatosGenerales(
    var nombre: String = "",
    var curp: String = "",
    var carrera: String = "",
    var planDeEstudios: String = "",
    var especialidad: String = "",
)
