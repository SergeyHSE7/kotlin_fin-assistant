package com.kotlinHSE.sergey.models

import kotlinx.serialization.Serializable

@Serializable
data class StockPrice(
        val date: String,
        val price: Double,
        val volume: Int
)
