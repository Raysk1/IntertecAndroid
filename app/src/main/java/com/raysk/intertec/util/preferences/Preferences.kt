package com.raysk.intertec.util.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raysk.intertec.alumno.Alumno
import java.io.File
import java.io.FileReader
import java.io.FileWriter


class Preferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
    var tutorialComplete: Boolean
        get() {
            return prefs.getBoolean("tutorialComplete", false)
        }
        set(value) {
            prefs.edit().putBoolean("tutorialComplete", value).apply()
        }

    companion object {
        /**
         * Nombre del archivo donde se guardan los datos del [Alumno]
         */
        val ALUMNO_FILE_NAME = "alumnoData.json"


        /** Comprueba si existe el archivo JSON en la ruta especificada
         *@param filesDir Ruta del archivo
         *@param fileName Nombre del archivo
         *@return Retorna True si el archivo existe */
        fun datosJsonExists(filesDir: File, fileName: String): Boolean {
            val alumnoDataFile = File(filesDir, fileName)
            return alumnoDataFile.exists()
        }

        /** Carga y convierte el archivo JSON a una instancia de [T]
         * @param filesDir Ruta del archivo
         * @param fileName Nombre del archivo
         * @return Retorna el objeto [T] si este existe, si no, retorna nulo
         * */
        inline fun <reified T> cargarDatos(filesDir: File, fileName: String): T? {
            return try {
                val dataFile = File(filesDir, fileName)
                val gson = Gson()
                val fileReader = FileReader(dataFile)
                val dataObject: T = gson.fromJson(fileReader, T::class.java)
                fileReader.close()
                dataObject
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

        }

        /** Elimina el archivo JSON si este existe
         * @param filesDir Ruta del archivo
         * @param fileName Nombre del archivo */
        fun eliminarDatosJson(filesDir: File, fileName: String) {
            val dataFile = File(filesDir, fileName)
            if (dataFile.exists()) {
                dataFile.delete()
            }
        }

        /** Convierte y guarda los datos de la instancia de [T] en un archivo JSON
         * @param filesDir Ruta del archivo
         * @param fileName Nombre del archivo
         * @param instance Objeto de tipo [T] a guardar
         * @return Retorna true si se guardo correctamente sino retorna false
         * */
        fun <T> guardarDatosJson(filesDir: File,fileName: String,instance: T): Boolean {
            return try {
                val dataFile = File(filesDir, fileName)
                val gson = GsonBuilder().setPrettyPrinting().create()
                val fileWriter = FileWriter(dataFile)
                gson.toJson(instance, fileWriter)
                fileWriter.close()
                true
            }catch (e:Exception){
                e.printStackTrace()
                false
            }
        }
    }
}