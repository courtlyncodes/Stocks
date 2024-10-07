package com.example.stocks.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.stocks.data.StocksRepository
import com.example.stocks.ui.StocksApplication
import com.example.stocks.ui.StocksListUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class StocksListViewModel(private val stocksRepository: StocksRepository) : ViewModel()  {
    var stocksUiState: StocksListUiState by mutableStateOf(StocksListUiState.Loading)
        private set

    // Initialize the ViewModel and load stocks data
    init {
        loadStocksData()
    }

    private fun loadStocksData() {
            viewModelScope.launch {
                stocksUiState = StocksListUiState.Loading
                stocksUiState = try {
                    val stocks = stocksRepository.loadStocksData()
                    if (stocks.isEmpty()) {
                        StocksListUiState.Error
                    } else {
                        StocksListUiState.Success(stocks)
                    }
                } catch (e: IOException) {
                    StocksListUiState.Error
                } catch (e: HttpException) {
                    StocksListUiState.Error
                }
            }
        }

    // Factory for StockListViewModel that takes StocksRepository as a dependency
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as StocksApplication)
                val stocksRepository = application.stocksContainer.stocksRepository
                StocksListViewModel(stocksRepository = stocksRepository)
            }
        }
    }
}
