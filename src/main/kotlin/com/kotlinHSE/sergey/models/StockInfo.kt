package com.kotlinHSE.sergey.models

import kotlinx.serialization.Serializable

@Serializable
data class StockInfo(
        val name: String,
        val acronym: String,
        val figi: String,
        val currency: String
)
