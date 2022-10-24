package com.example.informacindelvehculo

import kotlinx.serialization.Serializable

@Serializable
class RespuestaGetInspecciones (var result: List<Inspeccion>)