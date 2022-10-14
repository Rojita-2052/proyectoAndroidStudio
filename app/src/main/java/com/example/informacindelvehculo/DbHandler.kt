package com.example.informacindelvehculo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHandler(context: Context): SQLiteOpenHelper(context, "datos_db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.i("TPE", "onCreate: Base de datos OK")
        val sql_usuario = ("CREATE TABLE USUARIO (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "correo TEXT, contrasena TEXT, nombre TEXT, apellido TEXT)")
        db?.execSQL(sql_usuario)
        val sql_inspecciones = ("CREATE TABLE INSPECCION (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "correo TEXT, contrasena TEXT, nombre TEXT, apellido TEXT)")
        db?.execSQL(sql_inspecciones)
        Log.i("TPE", "onCreate: Base de datos OK")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    //MÉTODO QUE INSERTA UN USUARIO
    fun insertPersona():Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        //contentValues.put("RUT", "11.111.111-1")
        //contentValues.put("NOMBRE", "LEONARDO SALINAS")
        //contentValues.put("SUELDO", 1000)

        val success = db.insert("USUARIO", null, contentValues)
        db.close()
        return success
    }

    //MÉTODO QUE INSERTA UNA INSPECCION
    fun insertInspeccion():Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        //contentValues.put("RUT", "11.111.111-1")
        //contentValues.put("NOMBRE", "LEONARDO SALINAS")
        //contentValues.put("SUELDO", 1000)

        val success = db.insert("INSPECCION", null, contentValues)
        db.close()
        return success
    }

    //MÉTODO QUE OBTIENE TODAS LAS PERSONAS
    @SuppressLint("Range")
    fun viewEmployee():List<Usuario>{
        val listaUsuario: ArrayList<Usuario> = ArrayList<Usuario>()
        val sql = "SELECT id, correo, contrasena, nombre, apellido FROM USUARIO"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(sql, null)
        }catch (e: SQLiteException) {
            db.execSQL(sql)
            return ArrayList()
        }

        var id: Int
        var correo: String
        var contrasena: String
        var nombre: String
        var apellido: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                correo = cursor.getString(cursor.getColumnIndex("rut"))
                contrasena = cursor.getString(cursor.getColumnIndex("contrasena"))
                nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                apellido = cursor.getString(cursor.getColumnIndex("apellido"))

                val usuario = Usuario(id = id, correo = correo, contrasena = contrasena, nombre = nombre, apellido = apellido)
                listaUsuario.add(usuario)
            } while (cursor.moveToNext())
        }

        return listaUsuario
    }
}