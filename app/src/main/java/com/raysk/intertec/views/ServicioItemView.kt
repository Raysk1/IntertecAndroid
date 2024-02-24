package com.raysk.intertec.views


import android.util.Log
import android.view.View
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
    private val uiScope = CoroutineScope(Dispatchers.Main)

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
                uiScope.launch { imprimirReciboServicio(servicio) }
                Toasty.info(itemView.context, "Cargando").show()
            }
            btnBorrar.setOnClickListener {

                //recive onClickLister para cada boton
                Dialogs.alertaEliminar(
                    itemView.context,
                    { dialog, _ ->
                        run {

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

    /**
     * Funcion para imprimir el recibo
     */
    private suspend fun imprimirReciboServicio(servicio: Servicio) {
        withContext(Dispatchers.IO) {
            Alumno.alumno!!.imprimirServicio(servicio, itemView.context)
        }
    }

    /** Elimina el servicio en un hilo diferente */
    private suspend fun eliminarServicio(servicio: Servicio) {
        var eliminado = false
        val alumno = Alumno.alumno!!
        withContext(Dispatchers.IO) {
            try {

                alumno.eliminarServicio(servicio)
                eliminado = true
            } catch (e: Exception) {
                Log.e(this.javaClass.name, "eliminarServicio: error", e)
            }
        }

        withContext(Dispatchers.Main) {
            if (!eliminado ) {
                Toasty.error(itemView.context, "Error al eliminar").show()
                return@withContext
            }
            val parent = itemView.parent as RecyclerView
            val index = alumno.servicios.indexOf(servicio)
            alumno.servicios.remove(servicio)
            parent.adapter!!.notifyItemRemoved(index)
            Toasty.success(itemView.context, "Eliminado correctamente").show()
        }

    }


}