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
    var CORREO_INSPECTOR: String = "";
    val api = "https://www.fer-sepulveda.cl/API_PRUEBA2/api-service.php";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CORREO_INSPECTOR = db.userIsLogged()
        println("CORREO: " +CORREO_INSPECTOR)
        if (CORREO_INSPECTOR != "") {
            val intent= Intent(applicationContext, Opciones::class.java)
            startActivity(intent)
        }
    }

    fun credenciales(view: View)
    {
        if ( CORREO_INSPECTOR == "" ) {
            val usuario = findViewById<EditText>(R.id.txt_usuario).text.toString()
            val clave = findViewById<EditText>(R.id.txt_clave).text.toString()

            val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
            val json = "{\"nombreFuncion\":\"UsuarioLogin\", \"parametros\": [\"" + usuario + "\", \"" + clave + "\"]}"
            Log.i("TPE: ", json)
            val client = OkHttpClient()
            val body: RequestBody = RequestBody.create(mediaType, json)
            val request: Request =
                Request.Builder().url(api).post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("FSR: ERROR: " + e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonData = response.body()!!.string()
                    val obj = Json.decodeFromString<RespuestaAuth>(jsonData.toString())

                    if(obj.result == "LOGIN OK"){
                        val response = db.insertLogin(usuario, clave)
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

    }

    fun btnRegistro(view: View){
        val intent= Intent(this, Registro::class.java)

        startActivity(intent)
    }
}