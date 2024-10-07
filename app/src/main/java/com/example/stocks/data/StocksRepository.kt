package com.example.stocks.data

import com.example.stocks.model.StockX
import com.example.stocks.network.StocksApiService

interface StocksRepository {
    suspend fun loadStocksData(): List<StockX>
}

class DefaultStocksRepository(private val stocksApiService: StocksApiService) :
    StocksRepository {
    // Retrieves list of stocks from the data source
    override suspend fun loadStocksData(): List<StockX> {
        return try {
            val res = stocksApiService.fetchStocksList()
            if (res.isSuccessful) {
                res.body()?.stocks ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}