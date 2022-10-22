package com.example.informacindelvehculo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    val db: DbHandler = DbHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val isLogin =
        // TODO: ACA SE DEBE CONSULTAR A LA BD Y VER SI EXISTE UN USUARIO CREADO
    }

    fun credenciales(view: View)
    {
        val usuario = findViewById<EditText>(R.id.txt_usuario).text.toString()
        val clave = findViewById<EditText>(R.id.txt_clave).text.toString()

        val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
        val json = "{\"nombreFuncion\":\"UsuarioLogin\", \"parametros\": [\"" + usuario + "\", \"" + clave + "\"]}"
        Log.i("TPE: ", json)
        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(mediaType, json)
        val request: Request =
            Request.Builder().url("https://www.fer-sepulveda.cl/API_PRUEBA2/api-service.php").post(body).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("FSR: ERROR: " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()!!.string()
                val obj = Json.decodeFromString<RespuestaAuth>(jsonData.toString())

                if(obj.result == "LOGIN OK"){
                    // TODO: EL USUARIO CUANDO LOGUEA CORRECTAMENTE SE DEBE GUARDAR EN LA BASE DE DATOS LOCAL
                    val response = db.insertUsuario(usuario, clave)
                    Log.i("TPE", "onCreate: " + response)
                    val intent= Intent(applicationContext, Opciones::class.java)
                    startActivity(intent)
                    println("Entraste")
                } else {
                    println("Error en credenciales")
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Credenciales incorrectas",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })

    }

    fun btnRegistro(view: View){
        val intent= Intent(this, Registro::class.java)

        startActivity(intent)
    }
}