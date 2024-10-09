package com.example.stocks

import com.example.stocks.network.StocksApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class StocksApiServiceTest {

    @Test
    fun apiService_fetchStocksList_returnsNonEmptyList() = runBlocking {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://storage.googleapis.com/cash-homework/cash-stocks-api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val apiService = retrofit.create(StocksApiService::class.java)
        val response = apiService.fetchStocksList()

        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals(response.body()?.stocks?.size, 16)
        assertEquals(response.body()?.stocks?.get(0)?.ticker, "^GSPC")
    }
}