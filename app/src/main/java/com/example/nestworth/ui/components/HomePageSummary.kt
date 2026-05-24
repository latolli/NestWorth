package com.example.nestworth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nestworth.ui.utils.FormatMoney
import com.example.nestworth.ui.viewmodel.MainViewModel

@Composable
fun HomePageSummary(
    viewModel: MainViewModel
)
{
    val totalNW by viewModel.totalNetWorth.collectAsState()
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Left section contains total NW stuff
        Column(
            modifier = Modifier.fillMaxSize().weight(0.5f).padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Net wealth",
                style = MaterialTheme.typography.bodySmall)
            Text(text = FormatMoney(totalNW),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = Bold)
            Text(text = "+340 € this month",
                style = MaterialTheme.typography.bodySmall)
        }
        // Right section contains other details
        Column(modifier = Modifier.fillMaxSize().weight(0.5f).padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start) {

        }
    }
}