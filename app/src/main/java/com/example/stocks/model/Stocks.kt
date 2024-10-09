package com.example.stocks.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stock(
    val stocks: List<StockX>
)

@JsonClass(generateAdapter = true)
data class StockX(
    val currency: String,
    @Json(name = "current_price_cents") val currentPriceCents: Int,
    @Json(name = "current_price_timestamp") val currentPriceTimestamp: Int,
    val name: String,
    val quantity: Int?,
    val ticker: String
)