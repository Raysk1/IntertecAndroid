package com.raysk.intertec.alumno

/** Clase que contiene los datos de un servicio del alumno */
data class Servicio(
    var folio: String,
    var codigo: String,
    var descripcion: String,
    var importe: String,
    var vigencia: String,
    var solicitado: String,
    var value: String
)
