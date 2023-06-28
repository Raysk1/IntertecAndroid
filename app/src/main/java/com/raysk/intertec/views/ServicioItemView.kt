package com.raysk.intertec.views


import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kusu.loadingbutton.LoadingButton
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.alumno.Servicio
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/** Elemento utilizado para mostrar los Servicios */
class ServicioItemView(view: View) : ViewHolder(view) {
    private val tvDescricion: TextView = view.findViewById(R.id.tvDescripcion)
    private val tvImporte: TextView = view.findViewById(R.id.tvImporte)
    private val tvSolicitado: TextView = view.findViewById(R.id.tvSolicitado)
    private val tvVigencia: TextView = view.findViewById(R.id.tvVigencia)
    private val tvFolio: TextView = view.findViewById(R.id.tvFolio)
    private val btnPagar: LoadingButton = view.findViewById(R.id.btnPagar)
    private val btnBorrar: LoadingButton = view.findViewById(R.id.btnEliiminar)

    /**Renderiza los elementos dados
     * @param servicio Servicio a renderizar */
    fun render(servicio: Servicio) {
        servicio.apply {
            tvDescricion.text = descripcion
            tvFolio.text = folio
            tvImporte.text = importe
            tvSolicitado.text = solicitado
            tvVigencia.text = vigencia
            btnPagar.setOnClickListener {
                Alumno.alumno!!.imprimirServicio(this, WebView(itemView.context))
                Toasty.info(itemView.context, "Cargando").show()
            }
            btnBorrar.setOnClickListener {

                //recive onClickLister para cada boton
                Dialogs.alertaEliminar(
                    itemView.context,
                    { dialog, _ ->
                        run {
                            val uiScope = CoroutineScope(Dispatchers.Main)
                            uiScope.launch { eliminarServicio(this@apply) }
                        }
                        dialog.dismiss()
                    },
                    { dialog, _ ->
                        run { dialog.dismiss() }
                    }).show()
            }
        }
    }


    /** Elimina el servicio en un hilo diferente */
    private suspend fun eliminarServicio(servicio: Servicio) {
        var eliminado = false
        withContext(Dispatchers.IO) {
            try {

                Alumno.alumno!!.eliminarServicio(servicio)
                eliminado = true
            } catch (e: Exception) {
                Log.e(TAG, "eliminarServicio: error", e)
            }
        }

        withContext(Dispatchers.Main) {
            if (!eliminado || !Alumno.alumno!!.servicios.remove(servicio)) {
                Toasty.error(itemView.context, "Error al eliminar").show()
                return@withContext
            }
            val parent = itemView.parent as RecyclerView
            parent.adapter!!.notifyDataSetChanged()
            Toasty.success(itemView.context, "Eliminado correctamente").show()
        }

    }


}