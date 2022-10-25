package com.example.informacindelvehculo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
    }

    fun registroOk(view: View) {
        val correo = findViewById<EditText>(R.id.txt_correo_registro).text.toString()
        val clave = findViewById<EditText>(R.id.txt_contrasena_registro).text.toString()
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

    fun almacenar(view: View) {
        val correo = findViewById<EditText>(R.id.txt_correo_registro).text.toString()
        val contrasena = findViewById<EditText>(R.id.txt_contrasena_registro).text.toString()
        val nombre = findViewById<EditText>(R.id.txt_nombre_registro).text.toString()
        val apellido = findViewById<EditText>(R.id.txt_apellido_registro).text.toString()

        val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
        var json = "{\"nombreFuncion\":\"UsuarioAlmacenar\", \"parametros\": [\"" + correo + "\", \"" + contrasena + "\", \"" + nombre + "\", \"" + apellido + "\"]}"
        Log.i("TPE: ", json)
        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(mediaType, json)
        val request: Request =
            Request.Builder().url("https://www.fer-sepulveda.cl/API_PRUEBA2/api-service.php").post(body).build()



        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("TPE: ERROR: " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()!!.string()

                val obj = Json.decodeFromString<RespuestaAuth>(jsonData.toString())
                println(obj.result);
            }
        })
    }
}