package com.example.informacindelvehculo

import kotlinx.serialization.Serializable

@Serializable
class Inspeccion (var id: Int, var patente: Int, var marca: String, var color: String,
                 var fecha_ingreso: Int ,var kilometraje: Int, var motivo: String,
                 var rut: String, var nombre: String)