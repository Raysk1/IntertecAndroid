package com.raysk.intertec.views


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.raysk.intertec.R
import dev.shreyaspatil.MaterialDialog.AbstractDialog.OnClickListener
import dev.shreyaspatil.MaterialDialog.MaterialDialog


/**Crea [AlertDialog] con distinos fines*/
class Dialogs {
    companion object {
        /**Crea un [MaterialDialog] con un mensaje de confirmacion para eliminar
         * @param context Contexto en el que se mostrara la alerta
         * @param positiveOnClickListener Accion que se realizara al confirmar el mensaje
         * @param negativeOnClickListener Accion que se realizara al cancelar el mensaje
         * @return [MaterialDialog] Con el mensaje de confirmacion*/
        fun alertaEliminar(
            context: Context,
            positiveOnClickListener: OnClickListener,
            negativeOnClickListener: OnClickListener,
        ): MaterialDialog {
            val dialog = MaterialDialog.Builder(context as Activity) // set message, title, and icon
                .setTitle("Eliminar")
                .setMessage("¿Estas seguro de eliminar?")
                .setPositiveButton("Eliminar", R.drawable.ic_delete, positiveOnClickListener)
                .setNegativeButton("Cancelar", R.drawable.ic_close, negativeOnClickListener)
                .setAnimation(R.raw.delete_anim)
                .build()
            val animView = dialog.animationView
            animView.repeatCount = 0
            return dialog
        }

        fun alertaActualizacion(
            context: Context,
            positiveOnClickListener: OnClickListener,
            negativeOnClickListener: OnClickListener,
        ): MaterialDialog {
            val dialog = MaterialDialog.Builder(context as Activity) // set message, title, and icon
                .setTitle("Actualizacion Disponible")
                .setMessage("Hay una nueva actualizacion disponible.\n¿Deseas Actualizar?")
                .setPositiveButton("Actualizar", R.drawable.ic_update, positiveOnClickListener)
                .setNegativeButton("Cancelar", R.drawable.ic_close, negativeOnClickListener)
                .setAnimation(R.raw.update_anim)
                .build()
            val animView = dialog.animationView
            animView.repeatCount = 0
            return dialog
        }

        fun imageDialog(context: Context, drawable: Drawable): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.layout_image)
            val imageView = dialog.findViewById<ImageView>(R.id.imageUser)
            imageView.adjustViewBounds = true
            imageView.setImageDrawable(drawable)
            return dialog
        }
    }
}