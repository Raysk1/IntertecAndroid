package com.raysk.intertec.alumno
/** Clase que contine los datos personales del alumno */
data class DatosPersonales(
    var calle: String = "",
    var noCalle: String = "",
    var colonia: String = "",
    var ciudad: String = "",
    var cp: String = "",
    var telefono: String = "",
    var correoPersonal: String = "",
    var correoInstitucional: String = "",
    var fechaDeNacimiento: String = "",

)
