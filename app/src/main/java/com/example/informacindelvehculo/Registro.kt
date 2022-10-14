package com.example.informacindelvehculo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
    }

    fun registroOk(view: View) {
        val correo = findViewById<EditText>(R.id.txt_correo_registro).text.toString()
        val clave = findViewById<EditText>(R.id.txt_clave_registro).text.toString()
        val nombre = findViewById<EditText>(R.id.txt_nombre_registro).text.toString()
        val apellido = findViewById<EditText>(R.id.txt_apellido_registro).text.toString()

        val bundle = Bundle()
        bundle.putString("correo", correo)
        bundle.putString("clave", clave)
        bundle.putString("nombre", nombre)
        bundle.putString("apellido", apellido)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}