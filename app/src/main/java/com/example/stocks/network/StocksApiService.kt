package com.example.stocks.network

import com.example.stocks.model.Stock
import retrofit2.Response
import retrofit2.http.GET

interface StocksApiService {
    @GET("portfolio.json")
    suspend fun fetchStocksList(): Response<Stock>
}