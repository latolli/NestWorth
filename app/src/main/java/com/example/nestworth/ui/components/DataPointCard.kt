package com.example.nestworth.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nestworth.core.FormatMoney
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DataPointCard(
    date: Long,
    value: Double,
    liability: Double,
    onCardClick: () -> Unit,
) {
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
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display data
            Box(modifier = Modifier.weight(0.38f), contentAlignment = Alignment.CenterStart){
                // Convert date to something readable
                val displayDate = remember(date) {
                    Instant.ofEpochMilli(date)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                }
                Text(
                    text = displayDate,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start
                )
            }
            Box(modifier = Modifier.weight(0.38f), contentAlignment = Alignment.CenterStart){
                Text(
                    text = FormatMoney(value, "%.2f"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start
                )
            }
            Box(modifier = Modifier.weight(0.24f), contentAlignment = Alignment.CenterStart){
                Text(
                    text = FormatMoney(liability, "%.2f"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}