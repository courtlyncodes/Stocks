package com.example.stocks.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stocks.ui.viewmodel.StocksListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stocks.R
import com.example.stocks.model.StockX
import com.example.stocks.ui.StocksListUiState
import com.example.stocks.ui.theme.StocksTheme

@Composable
fun HomePane(
    modifier: Modifier = Modifier,
    stocksListViewModel: StocksListViewModel = viewModel(factory = StocksListViewModel.Factory)
) {
    when (val uiState = stocksListViewModel.stocksUiState) {
        is StocksListUiState.Success -> StocksList(stocks = uiState.stocks, modifier = modifier)
        is StocksListUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
        is StocksListUiState.Error -> ErrorScreen(modifier = modifier.fillMaxWidth())
}
}

@Composable
fun StocksList(
    stocks: List<StockX>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {
        items(items = stocks) { stock ->
            StockItem(stock = stock)
        }
    }
}

@Composable
fun StockItem(
    stock: StockX,
    modifier: Modifier = Modifier) {

    Card(modifier = modifier) {
        Column {
            Text(text = stock.name)
            Text(text = stock.ticker)
            Text(text = stock.currency)
            Text(text = stock.quantity.toString())
            Text(text = stock.currentPriceCents.toString())
            Text(text = stock.currentPriceTimestamp.toString())
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.loading),
        color = Color.Red,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(32.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.error),
        color = Color.Red,
        modifier = modifier.padding(16.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StocksTheme {

    }
}
