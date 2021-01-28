package com.kotlinHSE.sergey.models

import kotlinx.serialization.Serializable

@Serializable
data class StockResponse(
    val info: StockInfo,
    val priceHistory: List<StockPrice>,
    val sortValue: Double?,
    val sortValueView: String?
)
