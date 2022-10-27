package com.raysk.intertec.alumno

data class HorarioEvent(
    var id: Int = 0,
    var materia: String = "",
    var horaDeEntrada: Int = 0,
    var horaDeSalida: Int = 0,
    var aula: String = "",
    var dia: Int = 0,
    var color: Int = 0
)
