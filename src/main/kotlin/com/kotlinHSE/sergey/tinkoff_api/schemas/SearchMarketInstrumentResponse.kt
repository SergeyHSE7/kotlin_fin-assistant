package com.kotlinHSE.sergey.tinkoff_api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class SearchMarketInstrumentResponse(
        val trackingId: String,
        val status: String,
        val payload: MarketInstrument
)
