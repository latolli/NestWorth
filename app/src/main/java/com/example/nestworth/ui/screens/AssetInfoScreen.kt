package com.example.nestworth.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import com.example.nestworth.ui.viewmodel.MainViewModel

@Composable
fun AssetInfoScreen(
    viewModel: MainViewModel,
    assetId: Int,
    onBack: () -> Unit
) {
    val assets by viewModel.allAssets.collectAsState()
    val asset = assets.find { it.id == assetId }

    if (asset == null) {
        Text("Asset not found")
        return
    }

    Column()
    {
        Text("Asset Info Screen")
        Text(
            text = asset.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

    }
}