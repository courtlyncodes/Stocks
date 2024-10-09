package com.example.stocks

import com.example.stocks.data.DefaultStocksRepository
import com.example.stocks.fake.FakeStocksApiService
import com.example.stocks.fake.FakeStocksDataSource
import com.example.stocks.fake.MalformedDataSource.malformedData
import com.example.stocks.model.Stock
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import retrofit2.Response

class StocksRepositoryTest {

    @Test
    fun moshi_parseMalformedData_returnsNullOrError() = runTest {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(Stock::class.java)

        assertThrows(JsonEncodingException::class.java) {
            adapter.fromJson(malformedData)
        }
    }

    @Test
    fun stocksRepository_fetchEmptyStockData_returnsEmptyList(): Unit = runTest {
        val fakeStocksApiService = FakeStocksApiService()
        fakeStocksApiService.setResponse(Response.success(Stock(emptyList())))

        val repository = DefaultStocksRepository(
            stocksApiService = fakeStocksApiService
        )

        val exception = assertThrows(Exception::class.java) {
            runBlocking {
                repository.loadStocksData()
            }
        }
        assertEquals("No stocks found", exception.message)
    }

    @Test
    fun stocksRepository_loadStocksData_verifyStockList(): Unit = runTest {
        val fakeStocksApiService = FakeStocksApiService()

        val response = Response.success(Stock(FakeStocksDataSource.fakeStocksList))
        fakeStocksApiService.setResponse(response)

        val repository = DefaultStocksRepository(
            stocksApiService = fakeStocksApiService
        )

        val stocks = repository.loadStocksData()

        assertEquals(FakeStocksDataSource.fakeStocksList, stocks)
    }
}

