package com.raysk.intertec.util.preferences

import android.content.Context
import android.content.SharedPreferences


class Preferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
    var tutorialComplete:Boolean
        get(){
            return prefs.getBoolean("tutorialComplete",false)
        }
        set(value) {
            prefs.edit().putBoolean("tutorialComplete",value).apply()
        }

}