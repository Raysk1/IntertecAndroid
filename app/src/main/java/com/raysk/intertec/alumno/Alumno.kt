package com.raysk.intertec.alumno

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.print.PdfConverter
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raysk.intertec.alumno.Kardex.Companion.CURSADO
import com.raysk.intertec.alumno.Kardex.Companion.EN_CURSO
import com.raysk.intertec.alumno.Kardex.Companion.POR_CURSAR
import com.raysk.intertec.alumno.Kardex.Companion.REPITE
import com.raysk.intertec.alumno.Kardex.Companion.REPROBADO
import org.jsoup.Jsoup
import org.jsoup.nodes.TextNode
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException


class Alumno private constructor(var control: String, var password: String) {
    private var passwordToken: String? = null
    var datosGenerales: DatosGenerales = DatosGenerales()
    var datosPersonales: DatosPersonales = DatosPersonales()
    var datosAcademicos: DatosAcademicos = DatosAcademicos()
    var horario: ArrayList<HorarioEvent> = ArrayList()
    var kardex: Kardex = Kardex()
    var calificaciones: ArrayList<Calificaciones> = ArrayList()
    var promedioDelSemestreActual = 0f
    var parcialActual = 0
    var servicios: ArrayList<Servicio> = ArrayList()
    var catalogoDeServicios: ArrayList<Servicio> = ArrayList()
    var imagenURL = ""

    /** Funcion que valida si se ha iniciado correctamente la se sesion
     * @return Retorna True si se ha iniciado correctamente la sesion
     * */
    @Throws(IOException::class)
    fun validarInicioDeSesion(): Boolean {
        val url =
            "http://201.164.155.162/cgi-bin/sie.pl?Opc=MAIN&Control=$control&password=$password&aceptar=ACEPTAR"
        val document = Jsoup.connect(url).get()
        val title = document.title()
        if (title == "SIE Estudiantes") {
            return false
        }
        var pass = document.selectFirst("frame")!!.attr("src")
        pass = pass.substring(pass.indexOf("Password") + 9)
        passwordToken = pass.substring(0, pass.indexOf("&"))
        obtenerDatosDeAlumno()
        obtenerKardex()
        obtenerHorario()
        obtenerCalificaciones()
        obtenerServicios()
        obtenerCatalogoDeServicios()
        alumno = this
        return true
    }

    /**Obtiene los [DatosPersonales], [DatosAcademicos] y [DatosGenerales] del alumno*/
    @Throws(IOException::class)
    private fun obtenerDatosDeAlumno() {
        val url =
            "http://201.164.155.162/cgi-bin/sie.pl?Opc=DATOSALU&Control=$control&password=$passwordToken&aceptar=ACEPTAR"
        val document = Jsoup.connect(url).get()
        val imagen = document.select("img")[0]
        imagenURL = "http://201.164.155.162${imagen.attr("src")}"
        imagenURL = imagenURL.replace("f", "fintertec")

        var datos = document.select("strong")

        //Obteniendo los datos Generales
        datosGenerales.nombre = datos[3].text().trim { it <= ' ' }
        datosGenerales.curp = datos[4].text().trim { it <= ' ' }
        datosGenerales.carrera = datos[7].text().replace("\\(\\d+\\)".toRegex(), "")
            .trim { it <= ' ' }
        datosGenerales.planDeEstudios = datos[8].text().replace("\\(\\d+\\)".toRegex(), "")
        datosGenerales.especialidad = datos[9].text().replace("\\(\\d+\\)".toRegex(), "")
            .trim { it <= ' ' }

        //Obteniendo los datos Personales
        val cards = document.select("div.card-body")
        datos = cards[1].select("p")
        datosPersonales.calle = datos[0].text().trim { it <= ' ' }
        datosPersonales.noCalle = datos[1].text().trim { it <= ' ' }
        datosPersonales.colonia = datos[2].text().trim { it <= ' ' }
        datosPersonales.ciudad = datos[3].text().trim { it <= ' ' }
        datosPersonales.cp = datos[4].text().trim { it <= ' ' }
        datosPersonales.telefono = datos[5].text().trim { it <= ' ' }
        datosPersonales.correoPersonal = datos[6].text().trim { it <= ' ' }
        datosPersonales.correoInstitucional = "$control@eldorado.tecnm.mx"
        datosPersonales.fechaDeNacimiento = datos[8].text().trim { it <= ' ' }


        datos = cards[2].select("p")
        //Obteniendo los datos Academicos
        datosAcademicos.escuelaDeProcedencia = datos[0].text()
            .replace("\\s*\\(*\\d*\\)\\s*".toRegex(), "").trim { it <= ' ' }
        datosAcademicos.periodoDeIngreso = datos[1].text()
            .replace("\\(\\d+\\)".toRegex(), "").trim { it <= ' ' }
        datosAcademicos.periodosValidados = datos[2].text().trim { it <= ' ' }
        datosAcademicos.periodoActual = datos[3].text()
            .replace("\\(\\d+\\)".toRegex(), "").trim { it <= ' ' }
        datosAcademicos.creditosAcumulados = datos[4].text().trim { it <= ' ' }
        datosAcademicos.situacion = datos[5].text().trim { it <= ' ' }
    }

    /** Obtiene el [Kardex] del alumno */
    @Throws(IOException::class)
    private fun obtenerKardex() {
        val url =
            "http://201.164.155.162/cgi-bin/sie.pl?Opc=KARDEX&Control=$control&password=$passwordToken&aceptar=ACEPTAR"
        val document = Jsoup.connect(url).get()
        val tables = document.select("table")
        val trs = tables[1].select("tr")
        val data: MutableList<KardexData> = ArrayList()
        for (i in 2 until trs.size - 1) {
            val tr = trs[i]
            val tds = tr.select("td[style]")
            for (j in tds.indices) {
                val td = tds[j]
                val divs = td.select("div")
                val materia = td.childNodes()[2] as TextNode
                var calificacion = divs[2].text().trim { it <= ' ' }.split(" ").toTypedArray()[0]
                calificacion = calificacion.ifEmpty { "SC" }

                //Buscando por color para asignar el estado
                val divVaciosCount = divs.count { it.text() == "" }
                val style = td.attr("style")
                val rgb = style.substring(style.indexOf("rgba") + 5).trim().dropLast(1).split(",")
                val max = rgb.maxOfOrNull { it.toInt() }
                val estado = if (rgb.all { it == "255" }) {
                    POR_CURSAR
                } else if (rgb.indexOf(max.toString()) == 0) {
                    REPROBADO
                } else if (rgb.indexOf(max.toString()) == 1) {
                    if (divVaciosCount < 5) {
                        REPITE
                    } else {
                        EN_CURSO
                    }
                } else {
                    CURSADO

                }

                data.add(
                    KardexData(
                        divs[0].text().trim { it <= ' ' },
                        materia.text().trim { it <= ' ' },
                        calificacion,
                        (j + 1), estado
                    )
                )
            }
        }
        kardex.data = data.sortedBy { it.estado }.sortedBy { it.periodo }
        val promedio = tables[0].select("tr")[8].select("td")[1].text().trim { it <= ' ' }.toFloat()
        kardex.promedio = promedio
        kardex.creditosObtenidos =
            tables[0].select("tr")[5].select("td")[3].text().trim { it <= ' ' }
        kardex.creditosTotales = tables[0].select("tr")[5].select("td")[1].text().trim { it <= ' ' }
        val avance = tables[0].select("tr")[5].select("td")[5].text().trim { it <= ' ' }.toInt()
        kardex.avance = avance.toFloat()
    }

    /** Obtiene el [HorarioEvent] del alumno */
    @Throws(IOException::class)
    private fun obtenerHorario() {
        horario = ArrayList()
        val url =
            "http://201.164.155.162/cgi-bin/sie.pl?Opc=HORARIO&Control=$control&password=$passwordToken&aceptar=ACEPTAR"
        val document = Jsoup.connect(url).get()
        val tables = document.select("table")
        val trs = tables[1].select("tr")
        var id = 0
        val colors = arrayOf(
            "#bf9780",
            "#fdcae1",
            "#ffda89",
            "#84b6f4",
            "#ff6961",
            "#bae0f5",
            "#77dd77",
            "#98f6a9",
            "#bc98f3"
        )
        for (i in 1 until trs.size) {
            val tds = trs[i].select("td")
            for (j in 7..11) {
                val horaAula = tds[j].text().split(" ").toTypedArray()
                if (horaAula[0] == "") {
                    continue
                }
                val hora = horaAula[0].split("-").toTypedArray()
                val aula = horaAula[1]
                horario.add(
                    HorarioEvent(
                        id++,
                        tds[1].text().trim { it <= ' ' },
                        hora[0].split(":").toTypedArray()[0].toInt(),
                        hora[1].split(":").toTypedArray()[0].toInt(),
                        aula,
                        j - 6,
                        Color.parseColor(colors[i - 1]),
                        tds[0].text().trim { it <= ' ' }
                    )
                )
            }
        }
    }

    @Throws(IOException::class)
    /** Obtiene las [Calificaciones] del alumno */
    private fun obtenerCalificaciones() {
        calificaciones = ArrayList()
        promedioDelSemestreActual = 0f
        val url =
            "http://201.164.155.162/cgi-bin/sie.pl?Opc=CALIF&Control=$control&password=$passwordToken&aceptar=ACEPTAR"
        val document = Jsoup.connect(url).get()
        val tables = document.select("table")
        val trs = tables[1].select("tr")
        for (i in 1 until trs.size) {
            val tds = trs[i].select("td")
            var promedio = 0f
            val notas = ArrayList<Int>()
            for (j in 5 until tds.size) {
                val nota = tds[j].text().trim { it <= ' ' }
                if (nota != "") {
                    notas.add(nota.toInt())
                    promedio += nota.toInt()
                }
            }
            if (notas.size > parcialActual) {
                parcialActual = notas.size
            }
            promedio = if (notas.size > 0) promedio / notas.size else 0f

            promedioDelSemestreActual += promedio

            calificaciones.add(
                Calificaciones(
                    tds[0].text().trim { it <= ' ' },
                    tds[2].text().trim { it <= ' ' },
                    notas,
                    promedio
                )
            )
        }
        if (calificaciones.size > 0) {
            promedioDelSemestreActual /= calificaciones.size
        }
    }

    /** Convierte y guarda los datos del alumno en un archivo JSON */
    fun guardarDatosJson(filesDir: File) {
        //guardando datos del alumno
        val alumnoDataFile = File(filesDir, "alumnoData.json")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val fileWriter = FileWriter(alumnoDataFile)
        gson.toJson(this, fileWriter)
        fileWriter.close()
    }

    /** Cambia la contraseña del alumno
     * @param nuevoPassword Nueva contraseña del alumno
     * @return Retorna true si el cambio se realizo correctamente*/
    fun cambiarPassword(nuevoPassword: String): Boolean {
        val url =
            "http://201.164.155.162/cgi-bin/sie.pl?Opc=CAMBIARNIP&Control=$control&password=$passwordToken&Newpass=$nuevoPassword&aceptar=ACEPTAR"
        val document = Jsoup.connect(url).get()

        return if (document.title() == "Cambio de NIP") {
            password = nuevoPassword
            true
        } else {
            false
        }
    }

    /**Obtiene la lista de servicios activa del alumno */
    private fun obtenerServicios() {
        servicios = ArrayList()
        val url = "http://201.164.155.162/cgi-bin/sie.pl"
        val document = Jsoup.connect(url)
            .data("Opc", "PSERVICIOS")
            .data("Control", control)
            .data("Password", passwordToken!!)
            .data("psie", "intertec")
            .data("dummy", "0").get()
        val tables = document.select("table")
        val trs = tables[1].select("tr")
        for (i in 2 until trs.size) {
            val tds = trs[i].select("td")
            servicios.add(
                Servicio(
                    tds[1].text().trim(),
                    tds[2].text().trim(),
                    tds[3].text().trim(),
                    "$${tds[4].text().trim()}",
                    tds[5].text().trim(),
                    tds[6].text().trim(),
                    tds[0].selectFirst("input")!!.`val`().trim()
                )
            )

        }

    }

    /**Imprime o guarda en formato PDF el recibo del [Servicio]
     * @param servicio Servicio a imprimir o generar el recivo
     * @param context Contexto para la generacion del documento*/
    fun imprimirServicio(servicio: Servicio, context: Context) {
        val url =
            "http://201.164.155.162/cgi-bin/sie.pl?servicio=${servicio.value}&Opc=PSERVICIOS&Imprimir=Imprimir&Control=$control&Password=$passwordToken&psie=intertec&dummy=0"

        //val document = Jsoup.connect(url).get()
        val file = File(
            context.getExternalFilesDir(null),
            "${servicio.descripcion}-${servicio.folio}.pdf"
        )
        PdfConverter.instance?.convert(context, url, file)
    }

    /**Elimina un servicio de la lista de servicios activos del alumno
     * @param servicio [Servicio] a eliminar */
    @Throws(IOException::class)
    fun eliminarServicio(servicio: Servicio) {
        val url = "http://201.164.155.162/cgi-bin/sie.pl?servicio=${servicio.value}" +
                "&Opc=PSERVICIOS&Eliminar=Eliminar" +
                "&Control=$control&Password=$passwordToken&psie=intertec&dummy=0"
        Jsoup.connect(url).get()
    }

    /**Obtiene el catalogo de los servicios del alumno que se pueden agregar*/
    @Throws(IOException::class)
    private fun obtenerCatalogoDeServicios() {
        catalogoDeServicios = ArrayList()
        val url = "http://201.164.155.162/cgi-bin/sie.pl"
        val document = Jsoup.connect(url)
            .data("Opc", "SELSERVICIO")
            .data("Control", control)
            .data("Password", passwordToken!!)
            .data("psie", "intertec")
            .data("dummy", "0").get()

        val table = document.selectFirst("table")
        val trs = table!!.select("tr")
        for (i in 1 until trs.size) {
            val tds = trs[i].select("td")
            catalogoDeServicios.add(
                Servicio(
                    "",
                    tds[1].text().trim(),
                    tds[2].text().trim(),
                    tds[3].text().trim(),
                    tds[4].text().trim(),
                    "",
                    tds[0].selectFirst("input")!!.`val`().trim()
                )
            )

        }
    }

    /**Agrega un nuevo [Servicio] a la lista de [servicios] del alumno
     * @throws IOException*/
    fun agregarServicio(servicio: Servicio) {
        val url = "http://201.164.155.162/cgi-bin/sie.pl"
        val document = Jsoup.connect(url)
            .data("concepto", servicio.value)
            .data("Opc", "AGREGASER")
            .data("Agregar", "Agregar")
            .data("Control", control)
            .data("Password", passwordToken!!)
            .data("psie", "intertec")
            .data("dummy", "0")
            .data("tcll", datosPersonales.calle)
            .data(
                "tnum",
                datosPersonales.noCalle.ifEmpty { "          " }
            )
            .data(
                "tcol",
                datosPersonales.colonia.ifEmpty { "                              " }
            )
            .data("tciu", datosPersonales.ciudad)
            .data("tcpo", datosPersonales.cp)
            .data("tte1", datosPersonales.telefono.substring(0 until 10))
            .data("tte2", datosPersonales.telefono.substring(0 until 10))
            .data("tmai", datosPersonales.correoPersonal)
            .data("trfc", datosGenerales.curp.substring(0..8))
            .get()
        Log.i(TAG, "agregarServicio: ${document.baseUri()}")


        obtenerServicios()
    }

    companion object {
        @JvmStatic
        var alumno: Alumno? = null

        /** Crea una instancia de Alumno
         * @param control Numero de control del alumno
         * @param password Contraseña del alumno
         * @return Una nueva instancia de Alumno*/
        fun getAlumno(control: String, password: String): Alumno? {
            alumno = Alumno(control, password)
            return alumno
        }

        /** Comprueba si existe el archivo JSON con los datos del alumno en la ruta especificada
         *@param filesDir Ruta del archivo
         * @return Retorna True si el archivo existe */
        fun datosJsonExists(filesDir: File): Boolean {
            val alumnoDataFile = File(filesDir, "alumnoData.json")
            return alumnoDataFile.exists()
        }

        /** Carga y convierte el archivo JSON a una instancia de Alumno
         * @param filesDir Ruta del archivo
         * @return Retorna True si se cargo correctamente*/
        fun cargarDatosAlumno(filesDir: File): Boolean {
            return try {
                val alumnoDataFile = File(filesDir, "alumnoData.json")
                val gson = Gson()
                val fileReader = FileReader(alumnoDataFile)
                alumno = gson.fromJson(fileReader, Alumno::class.java)
                fileReader.close()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }

        }

        /** Elimina el archivo JSON con los datos del alumno si este existe */
        fun eliminarDatosJson(filesDir: File) {
            alumno = null
            val alumnoDataFile = File(filesDir, "alumnoData.json")
            if (alumnoDataFile.exists()) {
                alumnoDataFile.delete()
            }
        }
    }
}