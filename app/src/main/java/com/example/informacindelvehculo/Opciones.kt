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
        val intent= Intent(this, ListaInspecciones::class.java)

        startActivity(intent)
    }
}