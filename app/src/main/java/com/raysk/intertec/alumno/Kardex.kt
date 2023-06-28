package com.raysk.intertec.alumno

/** Clase que contine los datos del kardex del alumno */
data class Kardex(
    var data: List<KardexData> = emptyList<KardexData>(),
    var creditosTotales: String = "",
    var creditosObtenidos: String = "",
    var promedio: Float = 0f,
    var avance: Float = 0f,
) {
    companion object {
        const val CURSADO = 1
        const val EN_CURSO = 2
        const val POR_CURSAR = 3
        const val REPROBADO = 4
        const val REPITE = 5
    }
}
