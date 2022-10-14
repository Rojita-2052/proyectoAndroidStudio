package com.example.informacindelvehculo

import kotlinx.serialization.Serializable

@Serializable
class Usuario (var id: Int, var correo: String, var contrasena: String, val nombre: String, val apellido: String)