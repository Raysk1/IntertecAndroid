package com.raysk.intertec

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.kusu.loadingbutton.LoadingButton
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.alumno.Alumno.Companion.getAlumno
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {
    private lateinit var control: TextView
    private lateinit var password: TextView
    private lateinit var login: LoadingButton
    private lateinit var snackBarContainer: View
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilPassword: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        control = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.loginButton)
        snackBarContainer = findViewById(R.id.snackBar)
        tilUsername = findViewById(R.id.tilUsername)
        tilPassword = findViewById(R.id.tilPassword)

        control.doAfterTextChanged { validarControl() }
        password.doAfterTextChanged { validarPassword() }

        login.setOnClickListener {
            if (validarCampos()) {
                login.showLoading()
                uiScope.launch { conectar(control.text.toString(), password.text.toString()) }
            }
        }
    }

    /** valida los campos de numero de control y paswword*/
    private fun validarCampos(): Boolean {
        if (!validarControl()) {
            control.requestFocus()
            return false
        }
        if (!validarPassword()) {
            password.requestFocus()
            return false
        }
        return true
    }

    /** Valida el numero de control */
    private fun validarControl(): Boolean {
        if (control.text.toString().isEmpty()) {
            tilUsername.error = "Debe llenar el campo"
            return false
        }
        if (control.text.toString().length < 8) {
            tilUsername.error = "Debe contener 8 numeros"
            return false
        }
        tilUsername.error = null
        return true

    }

    /** Valida la contraseña */
    private fun validarPassword(): Boolean {
        if (password.text.toString().isEmpty()) {
            tilPassword.error = "Debe llenar el campo"
            return false
        }
        tilPassword.error = null
        return true

    }

    /** Muestra un Snackbar con un mensaje
     * @param Mensaje El mensaje a mostrar
     * @param color El color del Snackbar a mostrar*/
    private fun mostrarMensaje(Mensaje: String, color: Int = R.color.ligh_gray) {
        Snackbar.make(snackBarContainer, Mensaje, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, color))
            .setTextColor(Color.WHITE)
            .show()
    }

    /** corrutina que realiza la conexion al servidor para traer de vuelta los datos del alumno
     * @param control Numero de control del alumno
     * @param password Contraseña del alumno*/
    private suspend fun conectar(control: String, password: String) {
        var alumno: Alumno?
        var mensaje = ""
        var validado = false
        var guardado = false

        withContext(Dispatchers.Default) {
            try {
                alumno = getAlumno(control, password)
                validado = alumno!!.validarInicioDeSesion()
            } catch (e: Exception) {
                alumno = null
                mensaje = "Error de conexion"
                e.printStackTrace()
                login.isClickable = true
            }
        }

        withContext(Dispatchers.IO) {
            try {
                if (validado) {
                    alumno?.guardarDatosJson(filesDir)
                    guardado = true
                }
            } catch (e: Exception) {
                Log.e("Error", e.message!!)
                guardado = false
            }
        }

        //en hilo principal
        withContext(Dispatchers.Main) {

            if (alumno == null) {
                login.hideLoading()
                mostrarMensaje(mensaje, R.color.error)
                return@withContext
            }
            if (validado) {
                if (!guardado) {
                    Toasty.error(
                        this@LoginActivity,
                        "No se han guardado los datos",
                        Toasty.LENGTH_LONG
                    ).show()
                }
                val intent = Intent(this@LoginActivity, ButtonNavigation::class.java)
                startActivity(intent)
                finish()
            } else if (mensaje.isEmpty()) {
                mensaje = "Usuario o Contraseña Incorrecto"
                mostrarMensaje(mensaje, R.color.warning)
                login.hideLoading()
            }
        }
    }

}