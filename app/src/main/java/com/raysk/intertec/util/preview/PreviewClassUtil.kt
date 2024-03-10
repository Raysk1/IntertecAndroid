package com.raysk.intertec.util.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.raysk.intertec.alumno.Residencia


class ResidenciaProvider : PreviewParameterProvider<Residencia> {
    override val values: Sequence<Residencia> = sequenceOf(
        Residencia(
            "2024-03-06",
            "Propuesta Propia",
            "DESARROLLO DE APLICACIÓN MÓVIL PARA LA VISUALIZACIÓN DE DATOS DEL PERFIL ESTUDIANTIL DE LOS ESTUDIANTES DEL INSTITUTO TECNOLÓGICO SUPERIOR DE ELDORADO",
            "2024-03-06 a 2024-09-02",
            "INSTITUTO TECNOLOGICO SUPERIOR DE ELDORADO",
            "CINTHIA QUINTERO CONTRERAS",
            "En Revisión",
            "ALAIN EDUARDO ZAZUETA VALENZUELA",
            "100.00",
            "--"
        )
    )
}