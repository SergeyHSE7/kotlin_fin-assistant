package com.kotlinHSE.sergey.tinkoff_api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class MarketInstrumentListResponse(
        val trackingId: String,
        val status: String,
        val payload: MarketInstrumentList
)
