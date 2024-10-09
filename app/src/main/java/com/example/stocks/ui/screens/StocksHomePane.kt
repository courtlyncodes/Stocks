package com.example.stocks.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stocks.R
import com.example.stocks.model.StockX
import com.example.stocks.ui.StocksListUiState
import com.example.stocks.ui.viewmodel.StocksListViewModel
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun HomePane(
    onCardClick: (StockX) -> Unit,
    modifier: Modifier = Modifier,
    stocksListViewModel: StocksListViewModel = viewModel(factory = StocksListViewModel.Factory)
) {
    when (val uiState = stocksListViewModel.stocksUiState) {
        is StocksListUiState.Success -> StocksList(stocks = uiState.stocks, onCardClick = onCardClick, modifier = modifier.fillMaxWidth())
        is StocksListUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
        is StocksListUiState.Error -> ErrorScreen(modifier = modifier.fillMaxWidth())
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StocksListDetailPane(
    modifier: Modifier = Modifier
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<StockX>()

    val viewModel: StocksListViewModel = viewModel(factory = StocksListViewModel.Factory)
    var selectedStock by remember {
        mutableStateOf<StockX>(
            StockX(
                currency = "",
                currentPriceCents = 0,
                currentPriceTimestamp = 0,
                name = "",
                quantity = null,
                ticker = ""
            )
        )
    }

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.stocks))
                }
            )
        },
    ) { innerPadding ->
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                HomePane(
                    onCardClick = { stock ->
                        selectedStock = stock
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, stock)
                    },
                    modifier = Modifier
                )
            },
            detailPane = {
                StockDetail(
                    stock = selectedStock,
                    modifier = Modifier
                )
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun StockDetail(
    stock: StockX,
    modifier: Modifier = Modifier
) {
    Column {
        Text(text = stock.name)
        Text(text = stock.ticker)
        Text(text = stock.currency)
        Text(text = stock.quantity.toString())
        Text(text = stock.currentPriceCents.toString())
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StocksList(
    stocks: List<StockX>,
    onCardClick: (StockX) -> Unit,
    modifier: Modifier = Modifier
) {

        LazyColumn {
            items(stocks) { stock ->
                StockItem(
                    stock = stock,
                    onCardClick = onCardClick,
                    modifier = Modifier
                    )
            }
        }
    }


@Composable
fun StockItem(
    stock: StockX,
    onCardClick: (StockX) -> Unit,
    modifier: Modifier = Modifier
) {

    val truncatedName = truncateText(stock.name)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = CardDefaults.outlinedCardBorder(),
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = {onCardClick(stock)})
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier) {
                Row {
                    Text(text = stock.ticker)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (stock.quantity != null) "x${stock.quantity}" else "").toString()
                }
                Text(text = truncatedName)
            }
            Column(horizontalAlignment = Alignment.End, modifier = Modifier) {
                Text(text = "${stock.currentPriceCents} ${stock.currency}")
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.loading),
        color = MaterialTheme.colorScheme.error,
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
        color = MaterialTheme.colorScheme.error,
        modifier = modifier.padding(16.dp)
    )
}

private fun truncateText(text: String): String {
    return if (text.length > 20) {
        text.substring(0, 20) + "..."
    } else {
        text
    }
}