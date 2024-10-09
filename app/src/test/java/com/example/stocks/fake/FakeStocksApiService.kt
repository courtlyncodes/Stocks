package com.example.stocks.fake

import com.example.stocks.model.Stock
import com.example.stocks.network.StocksApiService
import retrofit2.Response

class FakeStocksApiService: StocksApiService {
    private var responseToReturn: Response<Stock>? = null

    fun setResponse(response: Response<Stock>) {
        responseToReturn = response
    }

    override suspend fun fetchStocksList(): Response<Stock> {
        return responseToReturn ?: throw Exception("Error loading stocks data")
    }
}