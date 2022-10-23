package com.example.informacindelvehculo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Opciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)
    }

    fun ingresarSolicitud(view: View){
        val intent= Intent(this, Home::class.java)

        startActivity(intent)
    }

    fun visualizarLista(view: View){
        // TODO: IMPLEMENTAR GET Y ENVIAR AL MY LIST ADAPTER
        val intent= Intent(this, ListarInspeccion::class.java)

        startActivity(intent)
    }
}