package com.kotlinHSE.sergey.tinkoff_api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class Candle(
        val figi: String,
        val interval: String,
        val o: Double,
        val c: Double,
        val h: Double,
        val l: Double,
        val v: Int,
        val time: String
)
