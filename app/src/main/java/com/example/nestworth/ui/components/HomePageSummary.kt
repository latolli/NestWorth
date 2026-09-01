package com.example.nestworth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.example.nestworth.R
import com.example.nestworth.core.LocalAppSettings
import com.example.nestworth.core.formatMoney
import com.example.nestworth.Repository.settings.Currency
import com.example.nestworth.ui.utils.SurfaceGroup
import com.example.nestworth.ui.viewmodel.MainViewModel

@Composable
fun HomePageSummary(
    totalNW: Double,
    networthGrowth: Double,
    highestEquityAsset: Pair<String, Double>?,
    savingsRateData: MainViewModel.SavingsRateResult
)
{
    val settings = LocalAppSettings.current
    val changeColorRes = when {
        networthGrowth < 0 -> colorResource(id = R.color.loss_red)
        networthGrowth > 0 -> colorResource(id = R.color.gain_green)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val addPlusSign = if (networthGrowth > 0) "+" else ""

    // Highest asset
    val assetName = when {
        highestEquityAsset == null -> "N/A"
        highestEquityAsset.first.length > 15 -> highestEquityAsset.first.take(12) + "..."
        else -> highestEquityAsset.first
    }
    val highestEquity = when {
        highestEquityAsset == null -> "N/A"
        else -> formatMoney(highestEquityAsset.second, settings.currency)
    }

    // Savings rate
    val windowSize = when {
        savingsRateData.isFallback -> "45-day"
        else -> "30-day"
    }
    val displayRate =
        if (savingsRateData.rate != 0.0) formatMoney(savingsRateData.rate!!, Currency.PERCENTAGE, decimalPlaces = 1)
        else "N/A"

    Row(
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        // Left section contains total NW stuff
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.45f)
                .padding(8.dp)
        ) {
            SurfaceGroup {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(text = "Net wealth",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = formatMoney(totalNW, settings.currency),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = Bold)

                    Text(
                        text = "${addPlusSign}${formatMoney(networthGrowth, settings.currency)} this month",
                        style = MaterialTheme.typography.bodySmall,
                        color = changeColorRes,
                    )
                }
            }
        }
        // Right section contains other details
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(0.55f)
            .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SurfaceGroup {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Text(text = "Savings rate ($windowSize)",
                        style = MaterialTheme.typography.bodySmall)
                    Text(text = displayRate,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = Bold)
                }
            }
            SurfaceGroup {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Text("Top Asset: $assetName", style = MaterialTheme.typography.bodySmall)
                    Text(text = highestEquity, style = MaterialTheme.typography.titleMedium, fontWeight = Bold)
                }
            }
        }
    }
}