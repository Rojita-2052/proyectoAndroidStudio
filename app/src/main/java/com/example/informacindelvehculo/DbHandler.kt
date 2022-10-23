package com.example.informacindelvehculo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date

class DbHandler(context: Context): SQLiteOpenHelper(context, "datos_db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.i("TPE", "onCreate: Base de datos OK")
        val sql_usuario = ("CREATE TABLE USUARIO (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CORREO TEXT, CONTRASENA TEXT)")
        db?.execSQL(sql_usuario)
        val sql_login = ("CREATE TABLE LOGIN (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CORREO TEXT, CONTRASENA TEXT, FECHA_INGRESO DATE)")
        db?.execSQL(sql_login)
        val sql_inspecciones = (
            "CREATE TABLE INSPECCION (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "MARCA TEXT, COLOR TEXT, FECHA_INGRESO TEXT, KILOMETRAJE, MOTIVO TEXT, MOTIVO_TEXTO TEXT, " +
            "RUT_CLIENTE TEXT, NOMBRE_CLIENTE TEXT)"
        )
        db?.execSQL(sql_inspecciones)
        Log.i("TPE", "onCreate: Base de datos OK")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    //MÉTODO QUE INSERTA UN USUARIO
    fun insertUsuario(correo: String, contrasena: String):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put("CORREO", correo)
        contentValues.put("CONTRASENA", contrasena)
        println("Usuario que se va ingresar: " + contentValues)
        val success = db.insert("USUARIO", null, contentValues)
        db.close()
        return success
    }

    fun insertLogin(correo: String, contrasena: String):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        contentValues.put("CORREO", correo)
        contentValues.put("CONTRASENA", contrasena)
        contentValues.put("FECHA_INGRESO", currentDate)
        println("Usuario que se esta logueando: " + contentValues)
        val success = db.insert("LOGIN", null, contentValues)
        db.close()
        return success
    }

    fun deleteLogin(correo: String) {
        val db = this.writableDatabase
        val sql_delete = ("DELETE FROM LOGIN WHERE CORREO = )"+ correo)
        db?.execSQL(sql_delete)
    }

    fun userIsLogged(): String {
        val sql = "SELECT CORREO, CONTRASENA, FECHA_INGRESO FROM LOGIN"
        val db = this.readableDatabase

        var CORREO: String = "";
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(sql, null)
        }catch (e: SQLiteException) {
            db.execSQL(sql)
        }


        if (cursor != null && cursor.moveToFirst()) {
            CORREO = cursor.getString(cursor.getColumnIndexOrThrow("CORREO"))
            cursor.close()
        }
        return CORREO;
    }

    //MÉTODO QUE INSERTA UNA INSPECCION
    fun insertInspeccion():Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        val success = db.insert("INSPECCION", null, contentValues)
        db.close()
        return success
    }

}