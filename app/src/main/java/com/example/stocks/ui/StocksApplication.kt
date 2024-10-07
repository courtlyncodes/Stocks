package com.example.stocks.ui

import android.app.Application
import com.example.stocks.data.DefaultStocksAppContainer
import com.example.stocks.data.StocksAppContainer

class StocksApplication : Application() {
    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var stocksContainer: StocksAppContainer

    override fun onCreate() {
        super.onCreate()
        stocksContainer = DefaultStocksAppContainer()
    }
}