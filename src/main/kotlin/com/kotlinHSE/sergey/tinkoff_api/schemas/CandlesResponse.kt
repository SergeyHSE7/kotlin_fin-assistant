package com.kotlinHSE.sergey.tinkoff_api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class CandlesResponse(
        val trackingId: String,
        val status: String,
        val payload: Candles
)
