package com.raysk.intertec

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kusu.loadingbutton.LoadingButton
import com.raysk.intertec.alumno.Alumno
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern


class ChangePassFragment : Fragment() {
    lateinit var tvPasswordActual: TextInputEditText
    lateinit var tvPasswordNew: TextInputEditText
    lateinit var tvPasswordNewConfirm: TextInputEditText
    lateinit var tilPasswordActual: TextInputLayout
    lateinit var tilPasswordNew: TextInputLayout
    lateinit var tilPasswordNewConfirm: TextInputLayout
    lateinit var guardarButton: LoadingButton
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvPasswordActual = view.findViewById(R.id.tvPasswordActual)
        tvPasswordNew = view.findViewById(R.id.tvPasswordNew)
        tvPasswordNewConfirm = view.findViewById(R.id.tvPasswordNewConfirm)
        tilPasswordActual = view.findViewById(R.id.tilPasswordActual)
        tilPasswordNew = view.findViewById(R.id.tilPasswordNew)
        tilPasswordNewConfirm = view.findViewById(R.id.tilPasswordNewConfirm)
        guardarButton = view.findViewById(R.id.guardarButton)

        tvPasswordActual.doAfterTextChanged { ValidarContraseñaActual() }
        tvPasswordNew.doAfterTextChanged { ValidarContraseñaNueva() }
        tvPasswordNewConfirm.doAfterTextChanged { ValidarConfirmarContraseña() }

        guardarButton.setOnClickListener {
            if (ValidarCampos()) {
                uiScope.launch { cambiarContraseña() }
            }
        }

    }

    private fun ValidarContraseñaActual(): Boolean {
        if (tvPasswordActual.text.toString().isEmpty()) {
            tilPasswordActual.error = "Debe llenar el campo"
            return false
        }
        if (tvPasswordActual.text.toString() != Alumno.alumno!!.password) {
            tilPasswordActual.error = "Contraseña incorrecta"
            return false
        }
        tilPasswordActual.error = null
        return true
    }

    private fun ValidarContraseñaNueva(): Boolean {
        if (tvPasswordNew.text.toString().isEmpty()) {
            tilPasswordNew.error = "Debe llenar el campo"
            return false
        }
        if (!Pattern.matches("^[A-Z]+.*", tvPasswordNew.text.toString())) {
            tilPasswordNew.error = "La contraseña debe iniciar con una mayuscula"
            return false
        }
        if (tvPasswordNew.text.toString().length < 8) {
            tilPasswordNew.error = "La contraseña debe contener al menos 8 caracteres"
            return false
        }
        if (tvPasswordNew.text.toString() == tvPasswordActual.text.toString()) {
            tilPasswordNew.error = "La nueva contraseña no puede ser igual a la actual"
            return false
        }
        tilPasswordNew.error = null
        return true
    }

    private fun ValidarConfirmarContraseña(): Boolean {
        if (tvPasswordNewConfirm.text.toString().isEmpty()) {
            tilPasswordNewConfirm.error = "Debe llenar el campo"
            return false
        }
        if (tvPasswordNew.text.toString() != tvPasswordNewConfirm.text.toString()) {
            tilPasswordNewConfirm.error = "La contraseña no coincide"
            return false
        }
        tilPasswordNewConfirm.error = null
        return true

    }

    private fun ValidarCampos(): Boolean {
        if (!ValidarContraseñaActual()) {
            tvPasswordActual.requestFocus()
            return false
        }
        if (!ValidarContraseñaNueva()) {
            tvPasswordNew.requestFocus()
            return false
        }
        if (!ValidarConfirmarContraseña()) {
            tvPasswordNewConfirm.requestFocus()
            return false
        }
        return true
    }


    private suspend fun cambiarContraseña() {
        withContext(Dispatchers.Main) {
            guardarButton.showLoading()
            guardarButton.isClickable = false
        }
        withContext(Dispatchers.IO) {
            try {
                Alumno.alumno!!.cambiarPassword(tvPasswordNew.text.toString())
                Alumno.alumno!!.guardarDatosJson(guardarButton.context.filesDir)
                withContext(Dispatchers.Main){
                    Toasty.success(guardarButton.context, "Se cambio la contraseña").show()
                    activity?.onBackPressed()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toasty.error(guardarButton.context, "Error de conexion").show()
                    guardarButton.hideLoading()
                    guardarButton.isClickable = true
                }
                e.message?.let { it1 -> Log.e("contraseña", it1) }
            }
        }

    }
}