package com.example.informacindelvehculo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
import java.io.IOException

class Opciones : AppCompatActivity() {
    val db: DbHandler = DbHandler(this)
    var CORREO_INSPECTOR: String = "";
    val ruta: String = "https://fer-sepulveda.cl/API_PRUEBA2/api-service.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)

        CORREO_INSPECTOR = db.userIsLogged()

        this.supportActionBar!!.title = CORREO_INSPECTOR

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

    fun scannearQR(view: View){
        val intent= Intent(this, QR::class.java)
        startActivity(intent)
    }

}