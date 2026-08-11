package com.example.nestworth.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun DisplayTrophy(
    trophyId: Int,
    unlocked: Boolean = true) {
    val achievement = AchievementCatalog.byId[trophyId] ?: return
    val shape = RoundedCornerShape(10.dp)
    var showPopup by remember { mutableStateOf(false) }
    val bdColor = if (unlocked) Color(achievement.color.toColorInt()) else Color.Gray
    val emoji = if (unlocked) achievement.emoji else "🔒"

    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(width = 45.dp, height = 45.dp)
            .clip(shape)
            .border(1.dp, bdColor, shape)
            .clickable(onClick = { showPopup = true }),
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji)
    }

    if (showPopup)
    {
        TrophyDetails(trophy = achievement, onDismiss = { showPopup = false })
    }
}