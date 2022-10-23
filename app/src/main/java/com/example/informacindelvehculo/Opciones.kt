package com.example.informacindelvehculo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Opciones : AppCompatActivity() {
    val db: DbHandler = DbHandler(this)
    var CORREO_INSPECTOR: String = "";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)

        CORREO_INSPECTOR = db.userIsLogged()

    }

    fun ingresarSolicitud(view: View){
        val intent= Intent(this, Home::class.java)
        startActivity(intent)
    }

    fun visualizarLista(view: View){
        val intent= Intent(this, ListarInspeccion::class.java)
        startActivity(intent)
    }

    fun logout(view: View) {
        db.deleteLogin(CORREO_INSPECTOR)
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}