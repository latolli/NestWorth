package com.example.nestworth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestworth.R
import com.example.nestworth.Repository.model.Asset
import com.example.nestworth.core.LocalAppSettings
import com.example.nestworth.core.formatMoney
import com.example.nestworth.Repository.settings.Currency

@Composable
fun AssetCard(
    asset: Asset,
    startEq: Double,
    currentEq: Double,
    onCardClick: () -> Unit,
    onAddClick: () -> Unit
) {
    val settings = LocalAppSettings.current
    // Growth is only meaningful once we have a real starting point.
    val hasBaseline = startEq > 0
    val growthAbsolute = currentEq - startEq
    val growthPercentage = if (hasBaseline) (currentEq - startEq) / startEq * 100 else 0.0

    val changeColorRes = when {
        growthAbsolute < 0 -> colorResource(id = R.color.loss_red)
        growthAbsolute > 0 -> colorResource(id = R.color.gain_green)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val addPlusSign = if (hasBaseline && growthAbsolute > 0) "+" else ""
    val growthAbsoluteText =
        if (hasBaseline) "${addPlusSign}${formatMoney(growthAbsolute, settings.currency)}"
        else "N/A"
    val growthPercentageText =
        if (hasBaseline) {
            val sign = if (growthPercentage > 0) "+" else ""
            "${sign}${formatMoney(growthPercentage, Currency.PERCENTAGE, decimalPlaces = 1)}"
        } else "N/A"

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
                            text = formatMoney(currentEq, settings.currency),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Start
                        )
                    }
                    Box(modifier = Modifier.weight(0.35f),
                        contentAlignment = Alignment.CenterStart)
                    {
                        Text(
                            text = growthAbsoluteText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = changeColorRes,
                            textAlign = TextAlign.Center
                        )
                    }
                    Box(modifier = Modifier.weight(0.2f),
                        contentAlignment = Alignment.CenterStart)
                    {
                        Text(
                            text = growthPercentageText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = changeColorRes,
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