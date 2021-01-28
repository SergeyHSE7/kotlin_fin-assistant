package com.kotlinHSE.sergey.tinkoff_api

import com.kotlinHSE.sergey.Stock
import com.kotlinHSE.sergey.models.*
import com.kotlinHSE.sergey.tinkoff_api.schemas.CandlesResponse
import com.kotlinHSE.sergey.tinkoff_api.schemas.MarketInstrumentListResponse
import com.kotlinHSE.sergey.tinkoff_api.schemas.SearchMarketInstrumentResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TinkoffApiService {
    companion object {
        private val apiLink: String = "https://api-invest.tinkoff.ru/openapi/sandbox/"
        private val token = System.getenv("api_token")

        private val stocksUrl = "${apiLink}market/stocks"
        private val candlesUrl = "${apiLink}market/candles"
        private val searchByTickerUrl = "${apiLink}market/search/by-ticker/"
        private val searchByFigiUrl = "${apiLink}market/search/by-figi?figi="


        fun getStocks(period: String, sortCriteria: String, isDescending: Boolean): List<StockResponse> {
            val json = getJsonResponse(stocksUrl)
            val response = Json.decodeFromString<MarketInstrumentListResponse>(json)

            val result = mutableListOf<StockResponse>()
            response.payload.instruments.take(20).forEach {
                val stock = Stock(
                        StockInfo(it.name, it.ticker, it.figi, it.currency),
                        getPriceHistory(it.figi, period),
                        sortCriteria
                )
                result.add(StockResponse(stock.info, stock.priceHistory, stock.sortValue, stock.sortValueView))
            }
            return if (isDescending)
                result.sortedByDescending { it.sortValue }
            else result.sortedBy { it.sortValue }
        }


        fun getStockByFigi(figi: String, period: String): StockResponse {
            val json = getJsonResponse(searchByFigiUrl + figi)
            val response = Json.decodeFromString<SearchMarketInstrumentResponse>(json)

            return StockResponse(
                    StockInfo(response.payload.name, response.payload.ticker, response.payload.figi, response.payload.currency),
                    getPriceHistory(figi, period),
                    null, null)
        }

        fun getPriceHistory(figi: String, period: String): List<StockPrice> {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            var interval = "5min"
            var from: LocalDateTime = LocalDateTime.now().minusDays(1)

            when (period) {
                "day" -> {
                    interval = "5min"
                    from = LocalDateTime.now().minusDays(1)
                }
                "week" -> {
                    interval = "hour"
                    from = LocalDateTime.now().minusWeeks(1)
                }
                "month" -> {
                    interval = "day"
                    from = LocalDateTime.now().minusMonths(1)
                }
                "year" -> {
                    interval = "day"
                    from = LocalDateTime.now().minusYears(1)
                }
                "decade" -> {
                    interval = "month"
                    from = LocalDateTime.now().minusYears(10)
                }
            }

            val json = getJsonResponse("$candlesUrl?" +
                    "figi=${figi}&" +
                    "from=${from.format(dateFormatter)}&" +
                    "to=${LocalDateTime.now().format(dateFormatter)}&" +
                    "interval=${interval}")
            val response = Json.decodeFromString<CandlesResponse>(json)

            val result = mutableListOf<StockPrice>()
            response.payload.candles.forEach { result.add(StockPrice(LocalDateTime.parse(it.time, dateFormatter).toString(), it.c, it.v)) }
            return result
        }

        private fun getJsonResponse(urlString: String = stocksUrl): String {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("Authorization", "Bearer $token")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.requestMethod = "GET"

            val jsonResponse = StringBuilder()
            val reader: BufferedReader = connection.inputStream.bufferedReader()
            reader.lines().forEach { jsonResponse.append(it) }
            reader.close()

            return jsonResponse.toString()
        }
    }
}
