package com.example.stocks.ui

import com.example.stocks.model.StockX

sealed interface StocksListUiState {
        data class Success(val stocks: List<StockX>) : StocksListUiState
        data object Error : StocksListUiState
        data object Loading : StocksListUiState
}