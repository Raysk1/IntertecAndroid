package com.raysk.intertec

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.raysk.intertec.alumno.Alumno
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)

        if (Alumno.datosJsonExists(filesDir)) {
            progressBar.visibility = View.VISIBLE
            uiScope.launch { actualizarDatos() }

        } else {
            iniciarLogin()
        }


    }

    /** Inicializa el activity Login */
    private fun iniciarLogin() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    /** Actualiza los datos del Alumno*/
    private suspend fun actualizarDatos() {
        var alumno: Alumno?
        var mensaje = ""
        var validado = false
        var guardado = false
        alumno = null

        withContext(Dispatchers.IO) {
            try {

                if (Alumno.cargarDatosAlumno(filesDir)) {
                    alumno = Alumno.alumno
                    validado = alumno!!.validarInicioDeSesion()
                }
            } catch (e: Exception) {
                mensaje = "No se han podido actualizar los datos"
                e.printStackTrace()
                return@withContext
            }

            try {
                if (validado) {
                    alumno?.guardarDatosJson(filesDir)
                    guardado = true
                    mensaje = "Se han actualizado los datos"
                }
            } catch (e: Exception) {

                guardado = false
                mensaje = "No se han podido guardar los datos"
            }
        }

        withContext(Dispatchers.Main) {
            if (alumno == null) {
                Toasty.error(this@MainActivity, mensaje, Toasty.LENGTH_LONG).show()
                iniciarLogin()
            } else {
                if (!guardado) {
                    Toasty.warning(this@MainActivity, mensaje, Toasty.LENGTH_LONG).show()
                } else if (validado) {
                    Toasty.success(this@MainActivity, mensaje, Toasty.LENGTH_LONG).show()
                }
            }
            val intent = Intent(this@MainActivity, ButtonNavigation::class.java)
            startActivity(intent)
            finish()
        }
    }
}