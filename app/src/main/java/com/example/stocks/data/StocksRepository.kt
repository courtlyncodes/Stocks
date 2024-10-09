package com.example.stocks.data

import android.util.Log
import com.example.stocks.model.StockX
import com.example.stocks.network.StocksApiService
import com.squareup.moshi.JsonEncodingException

interface StocksRepository {
    suspend fun loadStocksData(): List<StockX>
}

class DefaultStocksRepository(private val stocksApiService: StocksApiService) :
    StocksRepository {
    // Retrieves list of stocks from the data source
    override suspend fun loadStocksData(): List<StockX> {
        try {
            val response = stocksApiService.fetchStocksList()
            if (response.isSuccessful) {
                val stocks = response.body()?.stocks
                return if (stocks.isNullOrEmpty()) {
                    throw Exception("No stocks found")
                    emptyList()
                } else {
                    stocks
                }
            } else {
                throw Exception("Error loading stocks data: ${response.code()}")
            }
        } catch (e: JsonEncodingException) {
            Log.e("JsonEncodingException", "Error finding stocks data")
        }
        return emptyList()
    }
}