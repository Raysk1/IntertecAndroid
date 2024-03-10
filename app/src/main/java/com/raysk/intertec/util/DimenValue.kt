package com.raysk.intertec.util

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DimenValue {
    companion object{
        @Composable
        @ReadOnlyComposable
        fun spValue(@DimenRes id: Int) = dimensionResource(id = id).value.sp

        @Composable
        @ReadOnlyComposable
        fun dpValue(@DimenRes id: Int) = dimensionResource(id = id).value.dp

    }
}