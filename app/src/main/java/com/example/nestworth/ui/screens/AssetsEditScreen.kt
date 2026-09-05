package com.example.nestworth.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nestworth.Repository.model.AssetWithDatapoints
import com.example.nestworth.core.LocalAppSettings
import com.example.nestworth.ui.utils.SurfaceGroup
import com.example.nestworth.ui.utils.SurfaceRowDivider
import com.example.nestworth.ui.viewmodel.MainViewModel
import com.example.nestworth.ui.viewmodel.SettingsViewModel
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun AssetsEditScreen(
    viewModel: MainViewModel,
    settingsViewModel: SettingsViewModel,
    onBack: () -> Unit) {
    val settings = LocalAppSettings.current
    val assetsWithDatapoints by viewModel.allAssetsWithDatapoints.collectAsState()
    val assetsById = assetsWithDatapoints.associateBy { it.asset.id }

    // Recompute the "canonical" order whenever the underlying data or saved order changes
    val canonicalOrder = remember(assetsWithDatapoints, settings.assetOrder) {
        buildList {
            settings.assetOrder.forEach { assetId ->
                assetsById[assetId]?.let(::add)
            }
            assetsWithDatapoints
                .filter { it.asset.id !in settings.assetOrder }
                .forEach(::add)
        }
    }

    // This is the actual mutable, observable list the UI drags around
    val orderedAssets = remember { mutableStateListOf<AssetWithDatapoints>() }

    // Sync it whenever the canonical order changes (e.g. new asset added, initial load)
    LaunchedEffect(canonicalOrder) {
        orderedAssets.clear()
        orderedAssets.addAll(canonicalOrder)
    }

    val hapticFeedback = LocalHapticFeedback.current
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        orderedAssets.add(to.index, orderedAssets.removeAt(from.index))
        hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.surface)) {
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
                    contentDescription = "Back button",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            // Asset name
            Text(
                modifier = Modifier.weight(0.8f),
                text = "Edit Assets",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .weight(0.9f)
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            SurfaceGroup {
                if (orderedAssets.isNotEmpty()) {
                    LazyColumn (
                        state = lazyListState,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(orderedAssets, key = { it.asset.id }) {
                            ReorderableItem(reorderableLazyListState, key = it.asset.id) { isDragging ->
                                val bgColor = when {
                                    isDragging -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f)
                                    else -> androidx.compose.ui.graphics.Color.Transparent
                                }
                                ListItem(
                                    headlineContent = {
                                        Text(
                                            text = it.asset.name,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    },
                                    trailingContent = {
                                        IconButton(
                                            modifier = Modifier.draggableHandle(
                                                onDragStarted = {
                                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
                                                },
                                                onDragStopped = {
                                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureEnd)
                                                    settingsViewModel.updateAssetOrder(orderedAssets.map { it.asset.id })
                                                },
                                            ),
                                            onClick = {},
                                        ) {
                                            Icon(Icons.Rounded.List, contentDescription = "Reorder")
                                        }
                                    },
                                    colors = ListItemDefaults.colors(containerColor = bgColor)
                                )
                                if (it != orderedAssets.first()) {
                                    SurfaceRowDivider()
                                }
                            }
                        }
                    }
                }
                else {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Add a datapoint to get started")
                    }
                }
            }
        }

    }
}