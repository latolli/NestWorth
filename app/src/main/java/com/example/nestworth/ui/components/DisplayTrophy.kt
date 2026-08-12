package com.example.nestworth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.nestworth.achievement.AchievementCatalog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DisplayTrophy(
    trophyId: Int,
    unlocked: Boolean = true) {
    val achievement = AchievementCatalog.byId[trophyId] ?: return
    val shape = RoundedCornerShape(10.dp)
    var showPopup by remember { mutableStateOf(false) }
    val bdColor = if (unlocked) Color(achievement.color.toColorInt()) else Color.Gray
    val emoji = if (unlocked) achievement.emoji else "🔒"

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = achievement.rank, style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold, fontSize = 12.sp, color = bdColor)
        Box(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .size(width = 45.dp, height = 45.dp)
                .clip(shape)
                .border(2.dp, bdColor, shape)
                .clickable(onClick = { showPopup = true }),
            contentAlignment = Alignment.Center
        ) {
            // Draw a box inside the box to add transparent color layer
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(shape)
                    .border(2.dp, bdColor, shape)
                    .background(bdColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {}
            Text(text = emoji)
        }

    }

    if (showPopup)
    {
        TrophyDetails(trophy = achievement, onDismiss = { showPopup = false })
    }
}