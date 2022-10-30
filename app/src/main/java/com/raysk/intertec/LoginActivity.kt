package com.raysk.intertec

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kusu.loadingbutton.LoadingButton
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.alumno.Alumno.Companion.getAlumno
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

    fun validarControl(): Boolean {
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

    fun validarPassword(): Boolean {
        return if (password.text.toString().isEmpty()) {
            password.error = "Debe llenar el campo"
            false
        } else {
            true
        }
    }

    private fun mostrarMensaje(Mensaje: String) {
        Snackbar.make(snackBarContainer, Mensaje, Snackbar.LENGTH_LONG).show()
    }

    //Corrutina en 2do plano
    private suspend fun conectar(control: String, password: String){
        var alumno: Alumno?
        var mensaje = ""
        var validado = false

        withContext(Dispatchers.Default){
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
        
        //en hilo principal
        withContext(Dispatchers.Main) {
            if (alumno == null) {
                login.hideLoading()
                mostrarMensaje(mensaje)
                return@withContext
            }
            if (validado) {
                val intent = Intent(this@LoginActivity, ButtonNavigation::class.java)
                startActivity(intent)
                finish()
            } else {
                mensaje = "Usuario o Contraseña Incorrecto"
                mostrarMensaje(mensaje)
                login.hideLoading()
            }
        }
    }

}