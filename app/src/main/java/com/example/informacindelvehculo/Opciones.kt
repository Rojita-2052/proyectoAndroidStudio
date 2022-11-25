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

    fun AsistenciaAlmacenar(view: View){
        val intent= Intent(this, QR::class.java)
        startActivity(intent)

        val mensajeEntrada= findViewById<TextView>(R.id.txt_viewQR).text.toString()
        val correo  = findViewById<EditText>(R.id.txt_correo_registro).text.toString()
        val client = OkHttpClient()
        val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
        val parts = mensajeEntrada.split("|")
        val mensajeEntrada1 = parts[0]
        val mensajeEntrada2 = parts[1]

        val json = "{\"nombreFuncion\": \"AsistenciaAlmacenar\",\"parametros\": [\" "+ correo +"\", \" " + mensajeEntrada1 +"\"]}"
        val body: RequestBody = RequestBody.create(mediaType, json)
        val request: Request =  Request.Builder().url(ruta).post(body).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("TPE: La petición fallo")
            }

            override fun onResponse(call: Call, response: Response) {
                println("TPE: La petición funciono con éxito")

                println("TPE: " + response.body()?.string())
            }
        })
        println("Entraste")
        runOnUiThread{
            (Toast.makeText(applicationContext,
            "Bienvenido tu ingreso ha sido con fecha: " + mensajeEntrada2,Toast.LENGTH_LONG).show())
        }

    }

}