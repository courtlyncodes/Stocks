package com.example.stocks.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.example.stocks.network.StocksApiService

interface StocksAppContainer {
    val stocksRepository: StocksRepository
}

class DefaultStocksAppContainer : StocksAppContainer {
    private val baseUrl = "https://storage.googleapis.com/cash-homework/cash-stocks-api/"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // OkHttp client with logging interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Moshi instance with Kotlin adapter factory
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    // Retrofit instance with Moshi converter
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    // Retrofit service for making API calls
    private val retrofitService: StocksApiService by lazy {
        retrofit.create(StocksApiService::class.java)
    }

    // Stocks repository implementation
    override val stocksRepository: StocksRepository by lazy {
        DefaultStocksRepository(retrofitService)
    }
}
