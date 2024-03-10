package com.raysk.intertec.notas.residencia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.fragment.app.Fragment
import com.raysk.intertec.R.dimen
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.alumno.Residencia
import com.raysk.intertec.util.ColorUtils
import com.raysk.intertec.util.DimenValue.Companion.dpValue
import com.raysk.intertec.util.DimenValue.Companion.spValue
import com.raysk.intertec.util.preview.ResidenciaProvider
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowCircleDown
import compose.icons.fontawesomeicons.solid.ArrowCircleUp
import compose.icons.fontawesomeicons.solid.Building
import compose.icons.fontawesomeicons.solid.CalendarDay
import compose.icons.fontawesomeicons.solid.CalendarWeek
import compose.icons.fontawesomeicons.solid.CheckCircle
import compose.icons.fontawesomeicons.solid.FileContract
import compose.icons.fontawesomeicons.solid.GraduationCap
import compose.icons.fontawesomeicons.solid.InfoCircle
import compose.icons.fontawesomeicons.solid.UserCircle

class ResideciaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                Alumno.alumno?.residencia?.let { ResidenciaView(it) }
            }
        }
    }

    @Preview(showBackground = true, widthDp = 360, heightDp = 800)
    @Composable
    fun ResidenciaView(
        @PreviewParameter(
            ResidenciaProvider::class,
            limit = 1
        ) resindencia: Residencia,
    ) {

        val background = Brush.verticalGradient(colors = ColorUtils.backGroundGradientColors)
        Box(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(
                    top = dpValue(id = dimen.x20dp),
                    start = dpValue(id = dimen.x12dp),
                    end = dpValue(id = dimen.x12dp)
                )
            ) {
                Titulo(resindencia.proyecto)
                Box(
                    modifier = Modifier
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(
                                topEnd = dpValue(id = dimen.x12dp),
                                topStart = dpValue(id = dimen.x12dp)
                            )
                        )
                        .fillMaxSize()
                        .padding(dpValue(id = dimen.x10dp))
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dpValue(id = dimen.x10dp)),
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        DatoResindecia(
                            titulo = "Fecha de solicitud",
                            info = resindencia.fechaSolicitud,
                            icon = FontAwesomeIcons.Solid.CalendarDay
                        )
                        DatoResindecia(
                            titulo = "Opcion",
                            info = resindencia.opcion,
                            icon = FontAwesomeIcons.Solid.InfoCircle
                        )
                        DatoResindecia(
                            titulo = "duración",
                            info = resindencia.duracion,
                            icon = FontAwesomeIcons.Solid.CalendarWeek
                        )
                        DatoResindecia(
                            titulo = "empresa",
                            info = resindencia.empresa,
                            icon = FontAwesomeIcons.Solid.Building
                        )
                        DatoResindecia(
                            titulo = "asesor externo",
                            info = resindencia.asesorExterno,
                            icon = FontAwesomeIcons.Solid.UserCircle
                        )
                        DatoResindecia(
                            titulo = "dictamen",
                            info = resindencia.dictamen,
                            icon = FontAwesomeIcons.Solid.FileContract
                        )
                        DatoResindecia(
                            titulo = "Asesor interno",
                            info = resindencia.asesorInterno,
                            icon = FontAwesomeIcons.Solid.UserCircle
                        )
                        DatoResindecia(
                            titulo = "calificación",
                            info = resindencia.calificacion,
                            icon = FontAwesomeIcons.Solid.GraduationCap
                        )
                        DatoResindecia(
                            titulo = "liberacion",
                            info = resindencia.liberacion,
                            icon = FontAwesomeIcons.Solid.CheckCircle
                        )

                    }

                }
            }

        }

    }

    @Composable
    private fun Titulo(titulo: String) {
        var expandido by rememberSaveable { mutableStateOf(false) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = dpValue(id = dimen.x5dp)),
                text = "Nombre del proyecto:",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = spValue(id = dimen.x8sp)
            )
            Box(modifier = Modifier
                .clickable {
                    expandido = !expandido
                }
                .animateContentSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(
                        bottom = dpValue(
                            id = dimen.x7dp
                        )
                    )
                ) {
                    Text(
                        text = titulo,
                        textAlign = TextAlign.Center,
                        fontSize = spValue(dimen.x14sp),
                        color = Color.White,
                        modifier = Modifier
                            .padding(bottom = dpValue(id = dimen.x3dp)),
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = if (!expandido) 3 else 100
                    )
                    Icon(
                        imageVector = if (!expandido) FontAwesomeIcons.Solid.ArrowCircleDown else FontAwesomeIcons.Solid.ArrowCircleUp,
                        contentDescription = "Boton para expandir titulo",
                        modifier = Modifier.size(dpValue(id = dimen.x10sp)),
                        tint = Color.White
                    )
                }
            }
        }
    }


    @Composable
    private fun DatoResindecia(titulo: String, info: String, icon: ImageVector? = null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        icon, contentDescription = "$titulo del proyecto", modifier = Modifier.size(
                            dpValue(id = dimen.x10sp)
                        )
                    )
                }
                Text(
                    text = titulo.uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = spValue(id = dimen.x10sp),
                    modifier = Modifier.padding(
                        start = dpValue(
                            id = dimen.x3dp
                        )
                    )
                )
            }
            Text(
                text = info, fontSize = spValue(id = dimen.x8sp),
                modifier = Modifier.padding(
                    top = dpValue(
                        id = dimen.x4dp
                    )
                ),
                textAlign = TextAlign.Center,
            )

        }
    }

}