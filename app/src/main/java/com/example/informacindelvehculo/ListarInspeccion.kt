package com.example.informacindelvehculo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException

class ListarInspeccion : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_inspeccion)

        obtenerInspecciones("https://fer-sepulveda.cl/API_PRUEBA2/api-service.php?nombreFuncion=InspeccionObtener")
    }

    fun obtenerInspecciones(url: String) {
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("TPE: ERROR")
            }
            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()!!.string()
                println("TPE: " + jsonData)
                val obj = Json.decodeFromString<RespuestaGetInspecciones>(jsonData.toString())
                println("TPE: " + obj.result);

                for (p in obj.result) {
                    println(p.RUT_cLIENTE)
                }

                runOnUiThread {
                    viewRecord(obj.result)
                }
            }
        })
    }

    fun viewRecord(lista: List<Inspeccion>){
        val emp: List<Inspeccion> = lista

        val ID = Array<String>(emp.size){ "0"}
        val PATENTE = Array<String>(emp.size){"null"}
        val MARCA = Array<String>(emp.size){"null"}
        val COLOR = Array<String>(emp.size){"null"}
        val FECHA_INGRESO = Array<String>(emp.size){"null"}
        val KILOMETRAJE = Array<String>(emp.size){"null"}
        val MOTIVO = Array<String>(emp.size){"null"}
        val MOTIVO_TEXTO = Array<String>(emp.size){"null"}
        val RUT_cLIENTE = Array<String>(emp.size){"null"}
        val NOMBRE_CLIENTE = Array<String>(emp.size){"null"}
        val CORREO_INSPECTOR = Array<String>(emp.size){"null"}
        var index = 0

        for(e in emp){
            ID[index] = e.ID.toString()
            PATENTE[index] = e.PATENTE.toString()
            MARCA[index] = e.MARCA.toString()
            COLOR[index] = e.COLOR.toString()
            FECHA_INGRESO[index] = e.FECHA_INGRESO.toString()
            KILOMETRAJE[index] = e.KILOMETRAJE.toString()
            MOTIVO[index] = e.MOTIVO.toString()
            MOTIVO_TEXTO[index] = e.MOTIVO_TEXTO.toString()
            RUT_cLIENTE[index] = e.RUT_cLIENTE.toString()
            NOMBRE_CLIENTE[index] = e.NOMBRE_CLIENTE.toString()
            CORREO_INSPECTOR[index] = e.CORREO_INSPECTOR.toString()
            index++
        }

        var listView = findViewById<ListView>(R.id.listView)

        val myListAdapter = MyListAdapter(
            this, ID, PATENTE, MARCA, COLOR, FECHA_INGRESO,
            KILOMETRAJE, MOTIVO, MOTIVO_TEXTO, RUT_cLIENTE,
            NOMBRE_CLIENTE, CORREO_INSPECTOR
        )
        listView.adapter = myListAdapter
    }
}