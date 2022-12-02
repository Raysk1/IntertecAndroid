package com.raysk.intertec

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        control = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.loginButton)
        snackBarContainer = findViewById(R.id.snackBar)
        control.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                validarCampos()
            }
        })
        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                validarCampos()
            }
        })
        login.setOnClickListener {
            if (!validarControl()) {
                control.requestFocus()
            } else if (!validarPassword()) {
                password.requestFocus()
            } else {
                login.showLoading()
                uiScope.launch {
                    conectar(control.text.toString(), password.text.toString())
                }
            }
        }
    }

    private fun validarCampos() {
        validarControl()
        validarPassword()
    }

    private fun validarControl(): Boolean {
        return if (control.text.toString().isEmpty()) {
            control.error = "Debe llenar el campo"
            false
        } else if (control.text.toString().length < 8) {
            control.error = "Debe contener 8 numeros"
            false
        } else {
            true
        }
    }

    private fun validarPassword(): Boolean {
        return if (password.text.toString().isEmpty()) {
            password.error = "Debe llenar el campo"
            false
        } else {
            true
        }
    }

    private fun mostrarMensaje(Mensaje: String, color: Int = R.color.ligh_gray) {
        Snackbar.make(snackBarContainer, Mensaje, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, color))
            .setTextColor(Color.WHITE)
            .show()
    }

    //Corrutina en 2do plano
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
                guardado = false
            }
        }

        //en hilo principal
        withContext(Dispatchers.Main) {

            if (alumno == null) {
                login.hideLoading()
                mostrarMensaje(mensaje,R.color.error)
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
            } else if (mensaje.isEmpty()){
                mensaje = "Usuario o Contraseña Incorrecto"
                mostrarMensaje(mensaje,R.color.warning)
                login.hideLoading()
            }
        }
    }

}