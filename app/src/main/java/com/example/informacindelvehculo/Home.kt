package com.example.informacindelvehculo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException


class Home : AppCompatActivity() {
    var usuario = ""
    val db: DbHandler = DbHandler(this)
    var CORREO_INSPECTOR: String = "";
    val API: String = "https://www.fer-sepulveda.cl/API_PRUEBA2/api-service.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        CORREO_INSPECTOR = db.userIsLogged()
        val extras = intent.extras

        if (extras != null) {
            usuario = extras.getString("usuario").toString()
            this.supportActionBar!!.title = "Bienvenido: " + usuario
        }


        iniciarSpinnerMarca()
        iniciarSpinnerColor()

    }


    fun iniciarSpinnerMarca() {
        val opcionMarca = arrayOf("Seleccionar","Hyundai", "Chevrolet", "Susuki", "Nissan", "Chery")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionMarca)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerMarca = findViewById<Spinner>(R.id.sp_marca)
        spinnerMarca.adapter = adapter
    }

    fun iniciarSpinnerColor() {
        val opcionColor = arrayOf("Seleccionar","Rojo", "Blanco", "Negro", "Verde", "Gris")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionColor)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerColor = findViewById<Spinner>(R.id.sp_color)
        spinnerColor.adapter = adapter
    }

    fun selectMotivo(view: View) {
        /**
         * Función que sirve para desactivar el campo de texto de otro motivo cuando la
         * opción "Otro" no ha sido seleccionada
         */
        val rg_motivo = findViewById<RadioGroup>(R.id.rg_motivo)
        val radioSeleccionadoID = rg_motivo.checkedRadioButtonId
        val radioSeleccionado = findViewById<RadioButton>(radioSeleccionadoID)

        val txt_otro = findViewById<EditText>(R.id.txt_otro)
        Log.i("radio seleccionado: " , radioSeleccionado.text.toString())
        txt_otro.isEnabled = radioSeleccionado.text.toString() =="Otro"
    }

    fun guardarFormulario(view: View) {
        /**
         * Declaramos el builder y las variables que contienen los datos de la solicitud,
         * además de otras variables que utilizaremos para validar.
         */
        val txt_patente = findViewById<EditText>(R.id.txt_patente).text.toString()
        val txt_fecha_ingreso = findViewById<EditText>(R.id.txt_fecha_ingreso).text.toString()
        val txt_kilometraje = findViewById<EditText>(R.id.txt_kilometraje).text.toString()
        val sp_marca = findViewById<Spinner>(R.id.sp_marca)
        val sp_color = findViewById<Spinner>(R.id.sp_color)
        val txt_otro = findViewById<EditText>(R.id.txt_otro).text.toString()
        val txt_rut = findViewById<EditText>(R.id.txt_rut).text.toString()
        val txt_nombre = findViewById<EditText>(R.id.txt_nombre).text.toString()

        var motivo = ""
        var error = ""
        var validator = true

        /**
         * Validaremos los campos que no son obligatorios,
         * además obtenemos la marca y color selecciónados en los spinner como texto
         **/
        var marca = sp_marca.selectedItem.toString()
        var color = sp_color.selectedItem.toString()
        var patente = txt_patente

        if ( patente.isEmpty() ) patente = "No indica"
        if ( marca.equals("Seleccionar") ) marca = "No indica"
        if ( color.equals("Seleccionar") ) color = "No indica"

        /**
         * Validaciones a los campos que son obligatorios.
         */

        if ( txt_fecha_ingreso.isEmpty() ) {
            validator = false
            error = "Debe ingresar campo de fecha ingreso"
        } else if ( txt_kilometraje.isEmpty() ) {
            validator = false
            error = "Debe ingresar campo de kilometraje"
        } else if ( txt_rut.isEmpty() ) {
            validator = false
            error = "Debe ingresar campo de rut"
        } else if ( txt_nombre.isEmpty() ) {
            validator = false
            error = "Debe ingresar campo de nombre"
        }

        /**
         * Validación del motivo con try catch
         * Duda: Por que para los radio buttons se realiza de esta forma
         * y no como las anteriores validacion
         */
        try {
            val rg_motivo = findViewById<RadioGroup>(R.id.rg_motivo)
            val radioSeleccionadoID = rg_motivo.checkedRadioButtonId
            val radioSeleccionado = findViewById<RadioButton>(radioSeleccionadoID)
            motivo = radioSeleccionado.text.toString()
            if (motivo == "Otro") {
                motivo = txt_otro
            }
        } catch (err: Exception) {
            validator = false
            error = "Debe seleccionar un motivo"
        }

        /** Se validara el rut*/

        /**
         * Validación que sirve para determinar si cumple con los criterios de obligatorio,
         * si los datos son validos se envía un reporte, en caso contrario te notifica los campos
         * que falta por completar.
         */

        val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
        val json = "{\"nombreFuncion\":\"InspeccionAlmacenar\", \"parametros\": [\"" + patente +
                    "\", \"" + marca + "\", \"" + color + "\", \"" + txt_fecha_ingreso + "\", \"" +
                    txt_kilometraje + "\", \"" + motivo + "\", \"" + txt_otro + "\", \"" + txt_rut +
                    "\", \"" + txt_nombre + "\", \"" + CORREO_INSPECTOR + "\"]}"
        Log.i("TPE: ", json)
        val client = OkHttpClient()
        val body: RequestBody = RequestBody.create(mediaType, json)
        val request: Request =
            Request.Builder().url(API).post(body).build()

        if (validator) {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("TPE: ERROR: " + e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonData = response.body()!!.string()
                    println(jsonData)

                    val obj = Json.decodeFromString<RespuestaAuth>(jsonData.toString())
                    println(obj.result)
                    /**
                    if(obj.result != "NUEVO MENSAJE"){
                        val intent= Intent(applicationContext, Opciones::class.java)
                        startActivity(intent)
                        println("Entraste")
                    } else {
                        println("Fallo en el ingreso de la inspección")
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "Inspección mal ingresados",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }**/
                }
            })
        } else {
            val toast = Toast.makeText(this, error, Toast.LENGTH_LONG)
            toast.show()
        }
    }
}