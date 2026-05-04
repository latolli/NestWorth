package com.example.nestworth.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nestworth.R
import java.util.Locale
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.ui.components.AddAssetDatapoint
import com.example.nestworth.ui.components.AddAssetSheet
import com.example.nestworth.ui.viewmodel.MainViewModel

sealed class ActiveDialogType {
    data object None : ActiveDialogType()
    data object AddAsset : ActiveDialogType()
    data class AddData(val asset: Asset) : ActiveDialogType()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsScreen(
    viewModel: MainViewModel,
    onAssetClick: (Asset) -> Unit = {}
) {

    var activeDialog by remember { mutableStateOf<ActiveDialogType>(ActiveDialogType.None) }
    val assetsWithDatapoints by viewModel.allAssetsWithDatapoints.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        // Top summary section
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Summary section",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface)
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            thickness = 0.8.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
        )
        // List of assets
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.60f)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp)
        ) {
            items(assetsWithDatapoints) { assetWithDatapoints ->
                val sortedDatapoints = assetWithDatapoints.datapoints.sortedByDescending { it.date }
                val latestDatapoint = sortedDatapoints.getOrNull(0)
                val previousDatapoint = sortedDatapoints.getOrNull(1)
                val latestEquity = if (latestDatapoint != null){
                    latestDatapoint.value - latestDatapoint.liability
                } else 0.0
                val growth = if (previousDatapoint != null) {
                    val previousEquity = previousDatapoint.value - previousDatapoint.liability
                    if (previousEquity != 0.0) (latestEquity - previousEquity) / previousEquity * 100 else 0.0
                } else 0.0
                val firstDatapoint = sortedDatapoints.lastOrNull() // list is descending so last = oldest
                val growthSinceInception = if (firstDatapoint != null) {
                    val firstEquity = firstDatapoint.value - firstDatapoint.liability
                    if (firstEquity != 0.0) (latestEquity - firstEquity) / firstEquity * 100 else 0.0
                } else 0.0

                AssetItem(
                    asset = assetWithDatapoints.asset,
                    value = latestDatapoint?.value ?: 0.0,
                    liability = latestDatapoint?.liability ?: 0.0,
                    growth = growthSinceInception,
                    onCardClick = { onAssetClick(assetWithDatapoints.asset) },
                    onAddClick = { activeDialog = ActiveDialogType.AddData(assetWithDatapoints.asset) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    thickness = 0.8.dp,
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                )
            }
        }

        // Add new asset
        Button(
            onClick = { activeDialog = ActiveDialogType.AddAsset },
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Add new asset")
        }
    }

    // Check current active dialog
    if (activeDialog != ActiveDialogType.None) {
        when (val dialog = activeDialog) {
            is ActiveDialogType.AddAsset -> ModalBottomSheet(
                onDismissRequest = { activeDialog = ActiveDialogType.None },
                sheetState = sheetState
            ) {
                AddAssetSheet(
                onSave = { name, value, liability ->
                    viewModel.addAssetWithDatapoint(name, "Other", value, liability)
                    activeDialog = ActiveDialogType.None
                },
                onDismiss = { activeDialog = ActiveDialogType.None }
                )
            }
            is ActiveDialogType.AddData -> AddAssetDatapoint(
                asset = dialog.asset,
                onDismiss = { activeDialog = ActiveDialogType.None },
                onConfirm = { value, liability ->
                    viewModel.addDatapoint(dialog.asset, value, liability)
                    activeDialog = ActiveDialogType.None
                }
            )
            else -> {}
        }
    }
}

@Composable
fun AssetItem(
    asset: Asset,
    value: Double,
    liability: Double,
    growth: Double,
    onCardClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        //elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Placeholder
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home, // Using Home as a generic asset icon
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Asset Name
            Text(
                text = asset.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Asset Value Info
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,  // aligns children to the right
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${String.format(Locale.getDefault(), "%.2f", value - liability)} €",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )
                val changColorRes = if (growth < 0){
                    colorResource(id = R.color.loss_red)
                } else colorResource(id = R.color.gain_green)
                Text(
                    text = "${String.format(Locale.getDefault(), "%.2f", growth)}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = changColorRes,
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Add button for future data points
            IconButton(
                onClick = onAddClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add data point",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
