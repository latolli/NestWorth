package com.example.nestworth.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
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
import com.example.nestworth.ui.components.ConfirmationDialog
import com.example.nestworth.ui.components.CustomGraph
import com.example.nestworth.ui.viewmodel.MainViewModel

sealed class AssetInfoActiveDialogType {
    data object None : AssetInfoActiveDialogType()
    data object DeleteAsset : AssetInfoActiveDialogType()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssetInfoScreen(
    viewModel: MainViewModel,
    assetId: Int,
    onBack: () -> Unit,
    onEditHistory: () -> Unit
) {
    var activeDialog by remember { mutableStateOf<AssetInfoActiveDialogType>(AssetInfoActiveDialogType.None) }
    val assetsWithDatapoints by viewModel.allAssetsWithDatapoints.collectAsState()
    val assetData = assetsWithDatapoints.find { it.asset.id == assetId }
    val asset = assetData?.asset

    // Invalidity check
    if (asset == null) {
        Text("Asset not found")
        return
    }

    val sortedDatapoints = assetData.datapoints.sortedByDescending { it.date }
    val latestDatapoint = sortedDatapoints.getOrNull(0)

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
            .weight(0.1f),
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
                text = asset.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            // Delete button
            IconButton(
                modifier = Modifier.weight(0.1f),
                onClick = { activeDialog = AssetInfoActiveDialogType.DeleteAsset },
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete asset",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            thickness = 0.8.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                //.padding(horizontal = 24.dp, vertical = 4.dp)
                .padding(18.dp)
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            // Display graph
            val chartDatapoints = assetData.datapoints.sortedBy { it.date } // for the chart
            CustomGraph(chartDatapoints)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.3f)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {
            if (latestDatapoint != null)
            {
                val latestValue = latestDatapoint.value
                val latestLiability = latestDatapoint.liability
                Text(
                    text = "Equity: $${latestValue - latestLiability} €",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Value: $${latestValue} €",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Liability: $${latestLiability} €",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

            }
        }
        Button(
            onClick = { onEditHistory() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .weight(0.1f)
        ) {
            Text("Edit history")
        }

    }

    // Check current active dialog
    if (activeDialog != AssetInfoActiveDialogType.None) {
        when (val dialog = activeDialog) {
            is AssetInfoActiveDialogType.DeleteAsset -> ConfirmationDialog(
                onDismiss = { activeDialog = AssetInfoActiveDialogType.None },
                onConfirm = { viewModel.deleteAsset(asset)
                    activeDialog = AssetInfoActiveDialogType.None
                    onBack() },
                title = "Delete Asset '${asset.name}'",
                message = "This action cannot be undone."
            )
            else -> {}
        }
    }
}