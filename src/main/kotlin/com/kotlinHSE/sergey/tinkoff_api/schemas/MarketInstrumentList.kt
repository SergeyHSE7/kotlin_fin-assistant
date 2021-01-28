package com.kotlinHSE.sergey.tinkoff_api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class MarketInstrumentList(
        val total: Int,
        val instruments: Array<MarketInstrument>
)
