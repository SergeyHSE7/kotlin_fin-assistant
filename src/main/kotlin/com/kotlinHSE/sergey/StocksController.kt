package com.kotlinHSE.sergey

import com.kotlinHSE.sergey.tinkoff_api.TinkoffApiService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.stocksRouting() {
    route("/api/stocks") {
        get ("{period}/{sortCriteria}/{isDescending}") {
            return@get call.respond(TinkoffApiService.getStocks(
                call.parameters["period"]!!,
                call.parameters["sortCriteria"]!!,
                call.parameters["isDescending"]!!.toBoolean()
            ))
        }

        get("{figi}/{period}") {
            return@get call.respond(TinkoffApiService.getStockByFigi(
                call.parameters["figi"]!!,
                call.parameters["period"]!!
            ))
        }
    }
}
