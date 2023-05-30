package com.raysk.intertec.datos

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno.Companion.alumno
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage

class DatosMenuFragment : Fragment() {
    private lateinit var tvUsername: TextView
    private lateinit var tvUserNumControl: TextView
    private lateinit var avUser: AvatarView
    private lateinit var btDatosGenerales: Button
    private lateinit var btDatosPersonales: Button
    private lateinit var btDatosAcademicos: Button
    lateinit var tvUserCarrera: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

        avUser.loadImage(data = alumno.imagenURL, onError = {_,_ ->
            avUser.avatarInitials = icon
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
        tvUserCarrera.setBackgroundColor(
            when (alumno.datosGenerales.carrera) {
                "ISC" -> {
                    ContextCompat.getColor(requireContext(), R.color.ISCcolor)
                }
                "IIAL" -> {
                    ContextCompat.getColor(requireContext(), R.color.IIALcolor)
                }
                "INN" -> {
                    ContextCompat.getColor(requireContext(), R.color.INNcolor)
                }
                "IGE" -> {
                    ContextCompat.getColor(requireContext(), R.color.IGEcolor)
                }
                "IIAS" -> {
                    ContextCompat.getColor(requireContext(), R.color.IIAScolor)
                }
                else -> {
                    Color.TRANSPARENT
                }
            }
        )
    }
}