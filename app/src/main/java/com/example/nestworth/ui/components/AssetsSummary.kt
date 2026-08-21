package com.example.nestworth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nestworth.R
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

    val graphColors = listOf(
        colorResource(id = R.color.graph_gold),
        colorResource(id = R.color.graph_terracotta),
        colorResource(id = R.color.graph_muted_blue),
        MaterialTheme.colorScheme.onSurface)

    Row(modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center)
    {
        Box(modifier = Modifier.weight(0.55f),
            contentAlignment = Alignment.Center)
        {
            CircularProgressBar(topAssets, totalNetWorth.toFloat(), timeRangeNWGrowth, graphColors)
        }
        Column(modifier = Modifier.weight(0.45f).padding(end = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start)
        {
            SummaryValueRow("Total", formatMoney(totalNetWorth, settings.currency))

            // Display top 3 assets
            topAssets.forEach {item ->
                val itemLabel = if (item.name.length > 15) (item.name.take(12) + "...")
                    else item.name   // Max 15 characters
                SummaryValueRow(itemLabel,
                    formatMoney(item.equity, settings.currency),
                    labelColor = graphColors[topAssets.indexOf(item)],
                    valueColor = MaterialTheme.colorScheme.onSurface,
                    labelStyle = MaterialTheme.typography.titleSmall,
                    valueStyle = MaterialTheme.typography.titleSmall)
            }
            // Display equity of remaining assets
            if (othersEquity > 0) {
                SummaryValueRow("Others",
                    formatMoney(othersEquity, settings.currency),
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    valueColor = MaterialTheme.colorScheme.onSurface,
                    labelStyle = MaterialTheme.typography.titleSmall,
                    valueStyle = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Composable
private fun SummaryValueRow(
    label: String,
    value: String,
    labelColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    valueColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface,
    labelStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.titleLarge,
    valueStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.titleLarge
) {
    Row (
        modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = labelStyle,
            color = labelColor,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = value,
            style = valueStyle,
            fontWeight = FontWeight.SemiBold,
            color = valueColor
        )
    }
}