package com.kotlinHSE.sergey.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRate(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
