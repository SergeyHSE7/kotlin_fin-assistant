package com.kotlinHSE.sergey.tinkoff_api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class MarketInstrument(
        val figi: String,
        val ticker: String,
        val isin: String = "Not deffined",
        val minPriceIncrement: Double = 0.1,
        val lot: Int,
        val minQuantity: Int = 1,
        val currency: String = "???",
        val name: String,
        val type: String
)
