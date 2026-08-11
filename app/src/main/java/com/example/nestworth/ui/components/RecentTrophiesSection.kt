package com.example.nestworth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nestworth.achievement.AchievementCatalog

@Composable
fun RecentTrophiesSection(
    latestAchievements: List<Int>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 30.dp)
    ) {
        // Header section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(text = "Recent trophies", style = MaterialTheme.typography.bodySmall)
        }

        // Actual trophies
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            for (id in latestAchievements) {
                DisplayTrophy(trophyId = id)
            }
            if (latestAchievements.size < 5) {
                val remaining = 5 - latestAchievements.size
                val allAchievements = AchievementCatalog.ALL
                for (i in 1..remaining){
                    val achievement = allAchievements[i]
                    val isUnlocked = latestAchievements.contains(achievement.id)
                    if (!isUnlocked)
                    {
                        DisplayTrophy(trophyId = achievement.id, unlocked = false)
                    }
                }
            }
        }
    }
}