package com.example.nestworth.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestworth.R
import java.util.Locale
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.ui.components.AddAssetDatapoint
import com.example.nestworth.ui.components.AddAssetSheet
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


                AssetItem(
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
                    viewModel.addAssetWithDatapoint(name, "Other", value, liability)
                    activeDialog = AssetsActiveDialogType.None
                },
                onDismiss = { activeDialog = AssetsActiveDialogType.None }
                )
            }
            is AssetsActiveDialogType.AddData -> AddAssetDatapoint(
                asset = dialog.asset,
                onDismiss = { activeDialog = AssetsActiveDialogType.None },
                onConfirm = { value, liability, date ->
                    viewModel.addDatapoint(dialog.asset, value, liability, date)
                    activeDialog = AssetsActiveDialogType.None
                }
            )
            else -> {}
        }
    }
}

@Composable
fun AssetItem(
    asset: Asset,
    startEq: Double,
    currentEq: Double,
    onCardClick: () -> Unit,
    onAddClick: () -> Unit
) {
    val growthPercentage = if (startEq > 0) (currentEq - startEq) / startEq * 100
        else 0.0
    val growthAbsolute = currentEq - startEq
    Card(
        onClick = onCardClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
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
            Column(
                modifier = Modifier.weight(1f),
                //horizontalAlignment = Alignment.End,  // aligns children to the right
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Top row for asset name and icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    // Asset Name
                    Text(
                        text = asset.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Main row for value and growth
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Box(modifier = Modifier.weight(0.45f),
                    contentAlignment = Alignment.CenterStart)
                    {
                        Text(
                            text = "${String.format(Locale.getDefault(), "%.2f", currentEq)} €",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Start
                        )
                    }
                    val changColorRes = if (growthAbsolute < 0){
                        colorResource(id = R.color.loss_red)
                    } else colorResource(id = R.color.gain_green)
                    val growthPercentageText =
                        if (growthPercentage > 0) "+${String.format(Locale.getDefault(), "%.0f", growthPercentage)}%"
                        else "N/A"
                    val addPlusSign = if (growthAbsolute > 0) "+" else ""
                    Box(modifier = Modifier.weight(0.37f),
                        contentAlignment = Alignment.CenterStart)
                    {
                        Text(
                            text = "${addPlusSign}${
                                String.format(
                                    Locale.getDefault(),
                                    "%.0f",
                                    growthAbsolute
                                )
                            } €",
                            style = MaterialTheme.typography.bodyMedium,
                            color = changColorRes,
                            textAlign = TextAlign.Center
                        )
                    }
                    Box(modifier = Modifier.weight(0.18f),
                        contentAlignment = Alignment.CenterStart)
                    {
                        Text(
                            text = growthPercentageText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = changColorRes,
                            textAlign = TextAlign.End
                        )
                    }

                }
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
