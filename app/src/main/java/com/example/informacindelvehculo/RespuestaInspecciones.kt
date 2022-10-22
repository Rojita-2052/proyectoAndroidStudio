package com.example.informacindelvehculo

import kotlinx.serialization.Serializable

@Serializable
class RespuestaInspecciones (var result: List<Inspeccion>)