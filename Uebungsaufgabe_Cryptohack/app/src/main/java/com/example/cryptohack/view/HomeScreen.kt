package com.example.cryptohack.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cryptohack.network.CryptoCurrency
import kotlin.math.roundToInt

@Composable
fun HomeScreen() {
    val cryptos = listOf(
        CryptoCurrency("1", 1, "BTC", "Bitcoin", 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, ""),
        CryptoCurrency("2", 2, "ETH", "ETH", 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, "")
    )
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "CryptoHack",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 50.dp)
        )
        var showMenu by remember { mutableStateOf(false) }
        Box(modifier = Modifier.align(Alignment.End)) {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.MoreVert, "")
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(text = { Text("Price") }, onClick = { /*TODO*/ })
                DropdownMenuItem(text = { Text("MarketCap") }, onClick = { /*TODO*/ })
            }
        }

        LazyColumn {
            items(cryptos.size, key = { index -> cryptos[index].id }) { index ->
                CryptoRow(cryptos[index])
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun CryptoRow(cryptoCurrency: CryptoCurrency) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        Column {
            Text(text = cryptoCurrency.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            Text(text = cryptoCurrency.symbol, style = MaterialTheme.typography.titleSmall)
        }
        Text(
            ((cryptoCurrency.priceUsd * 100).roundToInt().toDouble() / 100).toString(),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

/*
fun loadData() {
    val assetService = Asset
    val scope = CoroutineScope(Job() + Dispatchers.IO)
    try {
        scope.launch {
            val res = assetService.assetService.getAllAssets().data
            println(res)
        }
    } catch (err: Error) {
        println(err)
    }
}
 */