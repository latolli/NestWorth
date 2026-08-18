package com.example.nestworth.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.example.nestworth.R
import com.example.nestworth.core.FormatMoney
import com.example.nestworth.ui.viewmodel.MainViewModel

@Composable
fun HomePageSummary(
    totalNW: Double,
    networthGrowth: Double,
    highestEquityAsset: Pair<String, Double>?,
    savingsRateData: MainViewModel.SavingsRateResult
)
{
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Left section contains total NW stuff
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f)
                .padding(start = 40.dp, top = 35.dp, end = 20.dp, bottom = 15.dp)
        ) {
            Text(text = "Net wealth",
                style = MaterialTheme.typography.bodySmall)
            Text(text = FormatMoney(totalNW),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = Bold)

            val changColorRes = if (networthGrowth < 0){
                colorResource(id = R.color.loss_red)
            } else colorResource(id = R.color.gain_green)
            val addPlusSign = if (networthGrowth > 0) "+" else ""
            Text(
                text = "${addPlusSign}${FormatMoney(networthGrowth)} this month",
                style = MaterialTheme.typography.bodySmall,
                color = changColorRes,
            )
        }
        // Right section contains other details
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(0.5f)
            .padding(start = 40.dp, top = 35.dp, end = 20.dp, bottom = 15.dp)
        ) {
            val windowSize = when {
                savingsRateData.isFallback -> "45-day"
                else -> "30-day"
            }
            val displayRate =
                if (savingsRateData.rate != 0.0) FormatMoney(savingsRateData.rate!!, "%.1f", "%")
                else "N/A"
            Text(text = "Savings rate ($windowSize)",
                style = MaterialTheme.typography.bodySmall)
            Text(text = displayRate,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = Bold)
            Spacer(modifier = Modifier.height(12.dp))
            highestEquityAsset?.let { (name, equity) ->
                Spacer(modifier = Modifier.height(12.dp))
                Text("Top Asset: $name", style = MaterialTheme.typography.bodySmall)
                Text(FormatMoney(equity), style = MaterialTheme.typography.titleMedium, fontWeight = Bold)
            }
        }
    }
}