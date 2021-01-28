package com.kotlinHSE.sergey.tinkoff_api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class Candles(
        val figi: String,
        val interval: String,
        val candles: Array<Candle>
)
