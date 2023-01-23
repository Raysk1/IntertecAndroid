package com.raysk.intertec.views

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface.OnClickListener

/**Crea [AlertDialog] con distinos fines*/
class AlertasDialog {
    companion object {
        /**Crea un [AlertDialog] con un mensaje de confirmacion para eliminar
         * @param context Contexto en el que se mostrara la alerta
         * @param positiveOnClickListener Accion que se realizara al confirmar el mensaje
         * @param negativeOnClickListener Accion que se realizara al cancelar el mensaje
         * @return [AlertDialog] Con el mensaje de confirmacion*/
        fun alertaEliminar(
            context: Context,
            positiveOnClickListener: OnClickListener,
            negativeOnClickListener: OnClickListener
        ): AlertDialog? {
            return AlertDialog.Builder(context) // set message, title, and icon
                .setTitle("Eliminar")
                .setMessage("¿Estas seguro de eliminar?")
                .setPositiveButton("Eliminar", positiveOnClickListener)
                .setNegativeButton("Cancelar", negativeOnClickListener)
                .create()
        }
    }
}