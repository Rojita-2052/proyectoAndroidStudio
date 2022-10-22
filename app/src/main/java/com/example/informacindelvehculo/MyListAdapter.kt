package com.example.informacindelvehculo

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter(
    private val context: Activity, private val ID: Array<String>,
    private val MARCA: Array<String>, private val COLOR: Array<String>,
    private val FECHA_INGRESO: Array<String>, private val KILOMETRAJE: Array<String>,
    private val MOTIVO: Array<String>, private val MOTIVO_TEXTO: Array<String>,
    private val RUT_cLIENTE: Array<String>, private val NOMBRE_CLIENTE: Array<String>,
    private val CORREO_INSPECTOR: Array<String>
) : ArrayAdapter<String>(context, R.layout.custom_list, ID) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val idText = rowView.findViewById(R.id.txt_id) as TextView
        // val patenteText = rowView.findViewById(R.id.txt_patente) as TextView
        val marcaText = rowView.findViewById(R.id.txt_marca) as TextView
        val colorText = rowView.findViewById(R.id.txt_color) as TextView
        val fechaIngresoText = rowView.findViewById(R.id.txt_fecha_ingreso) as TextView
        val kilometrajeText = rowView.findViewById(R.id.txt_kilometraje) as TextView
        val motivoText = rowView.findViewById(R.id.txt_motivo) as TextView
        val motivoTextoText = rowView.findViewById(R.id.txt_motivo_texto) as TextView
        val rutText = rowView.findViewById(R.id.txt_rut_cliente) as TextView
        val nombreText = rowView.findViewById(R.id.txt_nombre_cliente) as TextView
        val inspectorText = rowView.findViewById(R.id.txt_correo_inspector) as TextView

        idText.text = "ID: ${ID[position]}"
        // patenteText.text = "PATENTE: ${ID[position]}"
        marcaText.text = "ID: ${MARCA[position]}"
        colorText.text = "ID: ${COLOR[position]}"
        fechaIngresoText.text = "ID: ${FECHA_INGRESO[position]}"
        kilometrajeText.text = "ID: ${KILOMETRAJE[position]}"
        motivoText.text = "RUT: ${MOTIVO[position]}"
        motivoTextoText.text = "RUT: ${MOTIVO_TEXTO[position]}"
        rutText.text = "RUT: ${RUT_cLIENTE[position]}"
        nombreText.text = "RUT: ${NOMBRE_CLIENTE[position]}"
        inspectorText.text = "NOMBRE: ${CORREO_INSPECTOR[position]}"
        return rowView
    }
}