package com.raysk.intertec.util.update

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.google.gson.GsonBuilder
import com.raysk.intertec.views.Dialogs
import fuel.Fuel
import fuel.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Updates(val contex: Context) {


    /**
     * Funcion que busca si existe una actualizacion en el repo de Github y le informa al usuario
     */
    fun buscar() {
        val repoOwner = "raysk1"
        val repoName = "IntertecAndroid"

        val url = "https://api.github.com/repos/$repoOwner/$repoName/releases/latest"

        val uiScope = CoroutineScope(Dispatchers.IO)

        uiScope.launch {
            val body = Fuel.get(url).body
            val gson = GsonBuilder().create()
            val release = gson.fromJson(body, GitHubRelease::class.java)

            withContext(Dispatchers.Main) {
                val pInfo: PackageInfo =
                    contex.packageManager.getPackageInfo(contex.packageName, 0)

                val latestVersion = release.tagName
                val currentVersion = pInfo.versionName

                if (latestVersion != null) {
                    if (latestVersion > currentVersion) {
                        Dialogs.alertaActualizacion(
                            contex,
                            { dialog, _ ->
                                val intent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(release.assets[0].url))
                                startActivity(contex, intent, null)
                                dialog.dismiss()
                            },
                            { dialog, _ ->
                                dialog.dismiss()
                            }).show()

                    }
                }
            }
        }


    }

}