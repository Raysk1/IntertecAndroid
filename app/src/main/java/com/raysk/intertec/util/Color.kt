package com.raysk.intertec.util

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.model.IntegerHSLColor


class Color {
    companion object {
        fun randomHSLColor(pure: Boolean = false) = IntegerHSLColor.createRandomColor(pure)

        fun randomColor(pure: Boolean = false) = randomHSLColor(pure).toColorInt()

        fun textColor(text: String, @ColorRes color: Int, context: Context): String {
            var colorHex = ContextCompat.getString(context,color)
            colorHex = colorHex.substring(3)
            return "<font color=#$colorHex><b>$text</b></font>"
        }
    }
}