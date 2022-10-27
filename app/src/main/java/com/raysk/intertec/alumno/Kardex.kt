package com.raysk.intertec.alumno

data class Kardex(
    var data: List<KardexData> = emptyList<KardexData>(),
    var creditosTotales: String = "",
    var creditosObtenidos: String = "",
    var promedio: String = "",
    var avance: String = "",
    )
