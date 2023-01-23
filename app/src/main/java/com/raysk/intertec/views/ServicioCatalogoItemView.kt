package com.raysk.intertec.views

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.raysk.intertec.R
import com.raysk.intertec.alumno.Alumno
import com.raysk.intertec.alumno.Servicio
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServicioCatalogoItemView(view: View) : ViewHolder(view) {
    private val tvDescricion: TextView = view.findViewById(R.id.tvDescripcion)
    private val tvImporte: TextView = view.findViewById(R.id.tvImporte)
    private val tvVigencia: TextView = view.findViewById(R.id.tvVigencia)
    private val tvCodigo: TextView = view.findViewById(R.id.tvCodigo)
    private val btnAgregar: Button = view.findViewById(R.id.btnAgregar)
    private val uiScope = CoroutineScope(Dispatchers.Main)

    /**Renderiza los elementos dados
     * @param servicio Servicio a renderizar */
    fun render(servicio: Servicio) {
        servicio.apply {
            tvDescricion.text = descripcion
            tvCodigo.text = codigo
            tvImporte.text = importe
            tvVigencia.text = vigencia
            btnAgregar.setOnClickListener {
                uiScope.launch { agregarServicio(servicio) }
            }
        }
    }

    private suspend fun agregarServicio(servicio: Servicio) {
        var agregado = false
        withContext(Dispatchers.IO) {
            try {
                Alumno.alumno!!.agregarServicio(servicio)
                agregado = true
            } catch (e: Exception) {
                Log.e(
                    this@ServicioCatalogoItemView::class.toString(),
                    "agregarServicio: Error al agregar el servicio",
                    e
                )
            }
        }

        withContext(Dispatchers.Main) {
            if (agregado) {
                Toasty.success(itemView.context, "Se agrego correctamente").show()
            } else {
                Toasty.error(itemView.context, "Error al agregar el servicio").show()
            }
        }
    }
}