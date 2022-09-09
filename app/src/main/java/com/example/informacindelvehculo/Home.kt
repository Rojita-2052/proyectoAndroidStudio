package com.example.informacindelvehculo

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class Home : AppCompatActivity() {
    var usuario = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val extras = intent.extras

        if (extras != null) {
            usuario = extras.getString("usuario").toString()
            this.supportActionBar!!.title = "Bienvenido: " + usuario
        }

        iniciarMotivo()
        iniciarSpinnerMarca()
        iniciarSpinnerColor()

    }

    fun iniciarMotivo() {
        // var rg_motivo = findViewById<RadioGroup>(R.id.rg_motivo);
        // rg_motivo.isChecked = true;
    }

    fun iniciarSpinnerMarca() {
        var opcionMarca = arrayOf("Seleccionar","Hyundai", "Chevrolet", "Susuki", "Nissan", "Chery")

        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionMarca)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        var spinnerMarca = findViewById<Spinner>(R.id.sp_marca)
        spinnerMarca.adapter = adapter
    }

    fun iniciarSpinnerColor() {
        var opcionColor = arrayOf("Seleccionar","Rojo", "Blanco", "Negro", "Verde", "Gris")

        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionColor)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        var spinnerColor = findViewById<Spinner>(R.id.sp_color)
        spinnerColor.adapter = adapter
    }

    fun selectMotivo(view: View) {
        /**
         * Función que sirve para desactivar el campo de texto de otro motivo cuando la
         * opción "Otro" no ha sido seleccionada
         */
        val rg_motivo = findViewById<RadioGroup>(R.id.rg_motivo);
        val radioSeleccionadoID = rg_motivo.checkedRadioButtonId;
        val radioSeleccionado = findViewById<RadioButton>(radioSeleccionadoID);

        var txt_otro = findViewById<EditText>(R.id.txt_otro);
        Log.i("radio seleccionado: " , radioSeleccionado.text.toString());
        txt_otro.isEnabled = radioSeleccionado.text.toString() =="Otro";
    }


    fun validarFormulario(view: View) {
        /**
         * Declaramos el builder y las variables que contienen los datos de la solicitud,
         * además de otras variables que utilizaremos para validar.
         */
        val builder = AlertDialog.Builder(this);
        val txt_patente = findViewById<EditText>(R.id.txt_patente).text.toString();
        val txt_fecha_ingreso = findViewById<EditText>(R.id.txt_fecha_ingreso).text.toString();
        val txt_kilometraje = findViewById<EditText>(R.id.txt_kilometraje).text.toString();
        val sp_marca = findViewById<Spinner>(R.id.sp_marca);
        val sp_color = findViewById<Spinner>(R.id.sp_color);
        val txt_otro = findViewById<EditText>(R.id.txt_otro).text.toString();
        val txt_rut = findViewById<EditText>(R.id.txt_rut).text.toString();
        val txt_nombre = findViewById<EditText>(R.id.txt_nombre).text.toString();

        var motivo = "";
        var error = "";
        var validator = true;

        /**
         * Validaremos los campos que no son obligatorios,
         * además obtenemos la marca y color selecciónados en los spinner como texto
         **/
        var marca = sp_marca.selectedItem.toString();
        var color = sp_color.selectedItem.toString();
        var patente = txt_patente;

        if ( patente.isEmpty() ) patente = "No indica";
        if ( marca.equals("Seleccionar") ) marca = "No indica";
        if ( color.equals("Seleccionar") ) color = "No indica";

        /**
         * Validaciones a los campos que son obligatorios.
         */

        if ( txt_fecha_ingreso.isEmpty() ) {
            validator = false;
            error = "Debe ingresar campo de fecha ingreso";
        } else if ( txt_kilometraje.isEmpty() ) {
            validator = false;
            error = "Debe ingresar campo de kilometraje";
        } else if ( txt_rut.isEmpty() ) {
            validator = false;
            error = "Debe ingresar campo de rut";
        } else if ( txt_nombre.isEmpty() ) {
            validator = false;
            error = "Debe ingresar campo de nombre";
        };

        /**
         * Validación del motivo con try catch
         * Duda: Por que para los radio buttons se realiza de esta forma
         * y no como las anteriores validacion
         */
        try {
            val rg_motivo = findViewById<RadioGroup>(R.id.rg_motivo);
            val radioSeleccionadoID = rg_motivo.checkedRadioButtonId;
            val radioSeleccionado = findViewById<RadioButton>(radioSeleccionadoID);
            motivo = radioSeleccionado.text.toString();
            if (motivo == "Otro") {
                motivo = txt_otro;
            }
        } catch (err: Exception) {
            validator = false;
            error = "Debe seleccionar un motivo";
        }

        /** Se validara el rut*/

        /**
         * Validación que sirve para determinar si cumple con los criterios de obligatorio,
         * si los datos son validos se envía un reporte, en caso contrario te notifica los campos
         * que falta por completar.
         */
        if (validator) {
            with(builder) {
                setTitle("Reporte");
                setMessage(
                    "Usuario: " + usuario + "\n" +
                            "Patente: " + patente + "\n" +
                            "Modelo: " + marca + "\n" +
                            "Color: " + color + "\n" +
                            "Fecha de ingreso: " + txt_fecha_ingreso + "\n" +
                            "Kilometraje: " + txt_kilometraje + "\n" +
                            "Motivo: " + motivo + "\n" +
                            "Rut: " + txt_rut + "\n" +
                            "Nombre: " + txt_nombre
                );
                setPositiveButton("Enviar", DialogInterface.OnClickListener(function = sendButtonClick));
                setNegativeButton("Cancelar", cancelButtonClick);
                show();
            }
        } else {
            val toast = Toast.makeText(this, error, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    val sendButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(this,
            "Solicitud enviada correctamente", Toast.LENGTH_SHORT).show()
    }

    val cancelButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(this,
            "Solicitud no enviada", Toast.LENGTH_SHORT).show()
    }
}