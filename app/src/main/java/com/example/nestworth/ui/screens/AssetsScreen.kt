package com.example.nestworth.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.ui.components.AddAssetDatapoint
import com.example.nestworth.ui.components.AddAssetSheet
import com.example.nestworth.ui.components.AssetCard
import com.example.nestworth.ui.components.AssetsSummary
import com.example.nestworth.ui.viewmodel.MainViewModel

sealed class AssetsActiveDialogType {
    data object None : AssetsActiveDialogType()
    data object AddAsset : AssetsActiveDialogType()
    data class AddData(val asset: Asset) : AssetsActiveDialogType()
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsScreen(
    viewModel: MainViewModel,
    onAssetClick: (Asset) -> Unit = {}
) {

    var activeDialog by remember { mutableStateOf<AssetsActiveDialogType>(AssetsActiveDialogType.None) }
    val assetsWithDatapoints by viewModel.allAssetsWithDatapoints.collectAsState()
    val totalNW by viewModel.totalNetWorth.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val currentProfile by viewModel.latestProfile.collectAsState()
    val profile = currentProfile ?: return  // local val, smart-cast works fine

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        // Top summary section
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.3f)
        ) {
            AssetsSummary(assetsWithDatapoints, totalNW)
        }

        // TODO Should we add some option to define time range? Like last 1, 3, 6, etc months

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            thickness = 0.8.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
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
                val firstEquity = if (firstDatapoint != null) {
                    firstDatapoint.value - firstDatapoint.liability
                } else 0.0


                AssetCard(
                    asset = assetWithDatapoints.asset,
                    startEq = firstEquity ?: 0.0,
                    currentEq = latestEquity ?: 0.0,
                    onCardClick = { onAssetClick(assetWithDatapoints.asset) },
                    onAddClick = { activeDialog = AssetsActiveDialogType.AddData(assetWithDatapoints.asset) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    thickness = 0.8.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            }
        }

        // Add new asset
        Button(
            onClick = { activeDialog = AssetsActiveDialogType.AddAsset },
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text("Add new asset")
        }
    }

    // Check current active dialog
    if (activeDialog != AssetsActiveDialogType.None) {
        when (val dialog = activeDialog) {
            is AssetsActiveDialogType.AddAsset -> ModalBottomSheet(
                onDismissRequest = { activeDialog = AssetsActiveDialogType.None },
                sheetState = sheetState
            ) {
                AddAssetSheet(
                onSave = { name, value, liability ->
                    viewModel.addAssetWithDatapoint(profile, name, "Other", value, liability)
                    activeDialog = AssetsActiveDialogType.None
                },
                onDismiss = { activeDialog = AssetsActiveDialogType.None }
                )
            }
            is AssetsActiveDialogType.AddData -> AddAssetDatapoint(
                asset = dialog.asset,
                onDismiss = { activeDialog = AssetsActiveDialogType.None },
                onConfirm = { value, liability, date ->
                    viewModel.addDatapoint(profile, dialog.asset, value, liability, date)
                    activeDialog = AssetsActiveDialogType.None
                }
            )
            else -> {}
        }
    }
}
