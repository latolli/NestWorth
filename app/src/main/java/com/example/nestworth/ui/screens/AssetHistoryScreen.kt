package com.example.nestworth.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nestworth.Repository.model.AssetDatapoint
import com.example.nestworth.ui.components.DataPointCard
import com.example.nestworth.ui.components.EditAssetDatapoint
import com.example.nestworth.ui.viewmodel.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssetHistoryScreen(
    viewModel: MainViewModel,
    assetId: Int,
    onBack: () -> Unit)
{
    val assetsWithDatapoints by viewModel.allAssetsWithDatapoints.collectAsState()
    val assetData = assetsWithDatapoints.find { it.asset.id == assetId }
    val asset = assetData?.asset
    val currentProfile by viewModel.latestProfile.collectAsState()
    val profile = currentProfile ?: return  // local val, smart-cast works fine

    var showEditDialog by remember { mutableStateOf(false) }
    var currentDatapoint by remember { mutableStateOf<AssetDatapoint?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    )
    {
        // Top info bar
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .weight(0.10f),
            verticalAlignment = Alignment.CenterVertically) {
            // Back button
            IconButton(
                modifier = Modifier.weight(0.1f),
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Add data point",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            // Asset name
            Text(
                modifier = Modifier.weight(0.8f),
                text = asset?.name ?: "Asset not found",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            // Empty box to align text to center
            Box(modifier = Modifier.weight(0.1f))
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            thickness = 0.8.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )

        // List of asset datapoints sorted by date
        if (assetData != null && assetData.datapoints.isNotEmpty())
        {
            val sortedDatapoints = assetData.datapoints.sortedByDescending { it.date }
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.90f)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp)
            ) {
                items(sortedDatapoints) { dataPoint ->
                    DataPointCard(
                        date = dataPoint.date,
                        value = dataPoint.value,
                        liability = dataPoint.liability,
                        onCardClick = {
                            currentDatapoint = dataPoint
                            showEditDialog = true
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        thickness = 0.8.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                }
            }

        }
        else{
            Text("No data to display")
        }
    }

    // Display edit dialog
    if (showEditDialog) {
        EditAssetDatapoint(
            dataPoint = currentDatapoint!!,
            onDismiss = { showEditDialog = false },
            onConfirm = { value, liability, date ->
                viewModel.updateDatapoint(currentDatapoint!!, value, liability, date)
                showEditDialog = false
                viewModel.checkAchievements(profile, viewModel.totalNetWorth.value)
            },
            onDelete = { viewModel.deleteDatapoint(currentDatapoint!!); showEditDialog = false }
        )
    }
}