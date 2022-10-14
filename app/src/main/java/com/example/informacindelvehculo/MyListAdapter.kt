package com.example.informacindelvehculo

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter(private val context: Activity, private val id: Array<Int>,
                    private val patente: Array<String>, private val marca: Array<String>,
                    private val color: Array<String>, private val fecha_ingreso: Array<Int>,
                    private val kilometraje: Array<Int>, private val motivo: Array<String>,
                    private val rut: Array<String>, private val nombre: Array<String>)
    : ArrayAdapter<String>(context, R.layout.activity_home, rut) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.activity_home, null, true)
        /**
        val idText = rowView.findViewById(R.id.txt_id) as TextView
        val patenteText = rowView.findViewById(R.id.txt_patente) as TextView
        val marcaText = rowView.findViewById(R.id.txt_marca) as TextView
        val colorText = rowView.findViewById(R.id.txt_color) as TextView
        val fechaIngresoText = rowView.findViewById(R.id.txt_fecha_ingreso) as TextView
        val kilometrajeText = rowView.findViewById(R.id.txt_kilometraje) as TextView
        val motivoText = rowView.findViewById(R.id.txt_motivo) as TextView
        val rutText = rowView.findViewById(R.id.txt_rut) as TextView
        val nombreText = rowView.findViewById(R.id.txt_nombre) as TextView

        rutText.text = "ID: ${rut[position]}"
        rutText.text = "ID: ${rut[position]}"
        rutText.text = "ID: ${rut[position]}"
        rutText.text = "ID: ${rut[position]}"
        rutText.text = "ID: ${rut[position]}"
        nombreText.text = "RUT: ${nombre[position]}"
        apellidoText.text = "NOMBRE: ${apellido[position]}"
        sueldoText.text = "SUELDO: ${sueldo[position]}"
        **/
        return rowView
    }
}