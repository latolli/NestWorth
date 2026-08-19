package com.example.nestworth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.nestworth.Repository.model.AssetWithDatapoints
import com.example.nestworth.core.LocalAppSettings
import com.example.nestworth.core.formatMoney

data class AssetEquity(val name: String, val equity: Double)
@Composable
fun AssetsSummary(
    assetsWithDatapoints: List<AssetWithDatapoints>,
    totalNetWorth: Double,
    timeRangeNWGrowth: Double
){
    val settings = LocalAppSettings.current
    // Find 3 biggest assets
    val topAssets = assetsWithDatapoints
        .mapNotNull { asset ->
            val latest = asset.datapoints.maxByOrNull { it.date } // get latest datapoint
            latest?.let { AssetEquity(asset.asset.name, it.value - it.liability) }
        }
        .sortedByDescending { it.equity }
        .take(3)

    // Calculate equity for remaining assets
    val othersEquity = totalNetWorth - topAssets.sumOf { it.equity }


    Row(modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center)
    {
        Box(modifier = Modifier.weight(0.55f),
            contentAlignment = Alignment.Center)
        {
            CircularProgressBar(topAssets, totalNetWorth.toFloat(), timeRangeNWGrowth)
        }
        Column(modifier = Modifier.weight(0.45f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start)
        {
            // TODO: Add growth info to total NW
            Text(
                "Total: ${formatMoney(totalNetWorth, settings.currency)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            // Display top 3 assets
            topAssets.forEach {item ->
                Text(
                    "${item.name}: ${formatMoney(item.equity, settings.currency)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            // Display equity of remaining assets
            if (othersEquity > 0) {
                Text(
                    "Others: ${formatMoney(othersEquity, settings.currency)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}