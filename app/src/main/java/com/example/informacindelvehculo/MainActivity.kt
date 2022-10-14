package com.example.informacindelvehculo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun credenciales(view: View)
    {
        val usuario = findViewById<EditText>(R.id.txt_usuario).text.toString()
        val clave = findViewById<EditText>(R.id.txt_clave).text.toString()

        val bundle= Bundle()
        bundle.putString("usuario", usuario)
        bundle.putString("clave", clave)

        if(clave.equals("admin")){
            val intent= Intent(this, Home::class.java)
            intent.putExtras(bundle)

            startActivity(intent)
        }else{
            val toast = Toast.makeText(applicationContext,"Credenciales incorrectas", Toast.LENGTH_LONG)
                toast.show()
        }
    }

    fun btnRegistro(view: View){
        val intent= Intent(this, Registro::class.java)
        startActivity(intent)
    }
}