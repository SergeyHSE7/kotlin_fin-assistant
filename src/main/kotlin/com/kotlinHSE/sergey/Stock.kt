package com.kotlinHSE.sergey

import com.kotlinHSE.sergey.models.StockInfo
import com.kotlinHSE.sergey.models.StockPrice
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.roundToLong
import kotlin.math.sign


class Stock(
    val info: StockInfo,
    val priceHistory: List<StockPrice>,
    val sortCriteria: String
) {
    var sortValue: Double? = null
    var sortValueView: String? = null

    init {
        calculateSortValue()
        calculateSortPresentation()
    }

    private fun calculateSortValue() {
        sortValue =
                if (priceHistory.isEmpty()) null
                else
                    when (sortCriteria) {
                        "popularity" -> priceHistory.sumOf { it.volume.toLong() }.toDouble()
                        "price" -> priceHistory.last().price * (currencyRate[info.currency]
                                ?: error("Unknown currency!"))
                        "profit" -> priceHistory.last().price / priceHistory.first().price - 1
                        else -> null
                    }
    }

    private fun calculateSortPresentation() {
        sortValueView =
                if (sortValue == null) null
                else
                    when (sortCriteria) {
                        "popularity" -> "${sortValue!!.roundToLong()} шт."
                        "price" -> "${priceHistory.last().price.format(2)} ${currencySymbols[info.currency]}"
                        "profit" -> "${if (sortValue!!.sign > 0) "+" else ""}${(sortValue!! * 100).format(1)}%"
                        else -> null
                    }
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    companion object {
        private val currencySymbols = mapOf(Pair("USD", "$"), Pair("RUB", "руб."), Pair("EUR", "€"))
        private val currencyRate = mutableMapOf(Pair("USD", 74.36), Pair("RUB", 1.00), Pair("EUR", 90.41))


        fun startCurrencyUpdate() =
            Timer("Currency Update", false)
            .schedule(0, 24 * 60 * 60 * 1000, ) { getCurrencyRate() }

        private fun getCurrencyRate() {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val curDate = LocalDateTime.now().format(dateFormatter)
            val url = URL("https://www.currency-api.com/rates?base=RUB&date=${curDate}&symbols=USD,EUR")
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("Content-Type", "application/json")
            connection.requestMethod = "GET"

            val jsonResponse = StringBuilder()
            val reader: BufferedReader = connection.inputStream.bufferedReader()
            reader.lines().forEach { jsonResponse.append(it) }
            reader.close()

            val response = Json.decodeFromString<CurrencyRate>(jsonResponse.toString())
            currencyRate["USD"] = 1 / response.rates["USD"]!!
            currencyRate["EUR"] = 1 / response.rates["EUR"]!!
        }
    }


}
