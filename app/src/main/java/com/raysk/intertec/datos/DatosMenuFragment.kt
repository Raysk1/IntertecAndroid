package com.raysk.intertec.datos

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import coil.request.SuccessResult
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno.Companion.alumno
import com.raysk.intertec.views.Dialogs
import es.dmoral.toasty.Toasty
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage


class DatosMenuFragment : Fragment() {
    private lateinit var tvUsername: TextView
    private lateinit var tvUserNumControl: TextView
    private lateinit var avUser: AvatarView
    private lateinit var btDatosGenerales: Button
    private lateinit var btDatosPersonales: Button
    private lateinit var btDatosAcademicos: Button
    private lateinit var tvUserCarrera: TextView
    private var imagen: Drawable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_datos_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvUsername = view.findViewById(R.id.userName)
        avUser = view.findViewById(R.id.avUser)
        tvUserNumControl = view.findViewById(R.id.userNumControl)
        btDatosGenerales = view.findViewById(R.id.btDatosGenerales)
        btDatosPersonales = view.findViewById(R.id.btDatosPersonales)
        btDatosAcademicos = view.findViewById(R.id.btDatosAcademicos)
        tvUserCarrera = view.findViewById(R.id.tvUserCarrera)

        val alumno = alumno
        val navController = findNavController(view)
        val nombre = alumno!!.datosGenerales.nombre
        tvUsername.text = nombre
        val icon = nombre[0].toString() + ""

        avUser.loadImage(data = alumno.imagenURL,
            onError = { _, _ ->
                avUser.avatarInitials = icon
            },
            onSuccess = { _, successResult: SuccessResult ->
                val b = (successResult.drawable as BitmapDrawable).bitmap
                val dp = (400 * requireContext().resources.displayMetrics.density).toInt()
                val bitmapResized = Bitmap.createScaledBitmap(b, dp, dp, false)
                imagen = BitmapDrawable(resources, bitmapResized)
            })

        tvUserNumControl.text = alumno.control
        btDatosGenerales.setOnClickListener {
            navController.navigate(
                R.id.action_datosMenuFragment2_to_datosGeneralesFragment
            )
        }
        btDatosPersonales.setOnClickListener {
            navController.navigate(
                R.id.action_datosMenuFragment2_to_datosPersonalesFragment
            )
        }
        btDatosAcademicos.setOnClickListener {
            navController.navigate(
                R.id.action_datosMenuFragment2_to_datosAcademicosFragment
            )
        }
        tvUserCarrera.text = alumno.datosGenerales.carrera
        var colorId = 0
        var nombreCarrera = ""
        when (alumno.datosGenerales.carrera) {
            "ISC" -> {
                colorId = R.color.ISCcolor
                nombreCarrera = "Ingeniería en Sistemas Computacionales"
            }

            "IIAL" -> {
                colorId = R.color.IIALcolor
                nombreCarrera = "Ingeniería en Industrias Alimentarias"
            }

            "INN" -> {
                colorId = R.color.INNcolor
                nombreCarrera = "Ingeniería Industrial"
            }

            "IGE" -> {
                colorId = R.color.IGEcolor
                nombreCarrera = "Ingeniería en Gestión Empresarial"
            }

            "IIAS" -> {
                colorId = R.color.IIAScolor
                nombreCarrera = "Ingeniería en Innovación Agrícola Sustentable "
            }
        }
        tvUserCarrera.setBackgroundColor(ContextCompat.getColor(requireContext(), colorId))
        tvUserCarrera.setOnClickListener {
            context?.let { it1 ->
                Toasty.custom(
                    it1,
                    nombreCarrera,
                    R.drawable.rounded_ui,
                    colorId,
                    Toasty.LENGTH_LONG,
                    false,
                    true
                ).show()
            }
        }

        avUser.setOnClickListener {
            if (imagen != null) {
                Dialogs.imageDialog(requireContext(), imagen!!).show()
            }
        }
    }
}