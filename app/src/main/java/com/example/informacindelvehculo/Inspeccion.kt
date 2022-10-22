package com.example.informacindelvehculo

import kotlinx.serialization.Serializable

@Serializable
/**
 * c con minuscula y falta la patente
 */
class Inspeccion (
    var ID: String, var MARCA: String, var COLOR: String,
    var FECHA_INGRESO: String ,var KILOMETRAJE: String,
    var MOTIVO: String, var MOTIVO_TEXTO: String?,
    var RUT_cLIENTE: String, var NOMBRE_CLIENTE: String,
    var CORREO_INSPECTOR: String
)