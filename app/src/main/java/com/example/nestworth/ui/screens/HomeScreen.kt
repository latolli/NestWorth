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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nestworth.ui.components.XpProgressBar
import com.example.nestworth.ui.viewmodel.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.nestworth.Repository.model.Profile
import com.example.nestworth.ui.components.HomePageSummary
import com.example.nestworth.ui.components.LogExpenseSheet
import com.example.nestworth.ui.components.RecentTrophiesSection
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onProfileClick: (Int) -> Unit
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val currentProfile by viewModel.latestProfile.collectAsState()
    val profile = currentProfile ?: return  // local val, smart-cast works fine

    // Check daily login streak
    val now = System.currentTimeMillis()
    val zone = ZoneId.systemDefault()
    val lastLoginDate = Instant.ofEpochMilli(profile.lastLogin).atZone(zone).toLocalDate()
    val todayDate = Instant.ofEpochMilli(now).atZone(zone).toLocalDate()

    val newStreak = when {
        profile.dailyStreak == 0 -> 1                                                  // first login, streak starts
        lastLoginDate == todayDate -> profile.dailyStreak                              // already logged in today, no change
        lastLoginDate.plusDays(1) == todayDate -> profile.dailyStreak + 1  // consecutive day, continue streak
        else -> 1                                                                      // missed a day+, streak resets
    }

    LaunchedEffect(profile.id, profile.lastLogin) {
        if (profile.dailyStreak != newStreak) {
            viewModel.updateProfile(
                profile, profile.name, profile.xpAmount, profile.xpLevel,
                profile.achievements, newStreak, now
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        // Top section (profile icon, settings, etc)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.05f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            IconButton(onClick = { onProfileClick(profile.id) }) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile"
                )
            }
            Text(text = profile.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface)
            // TODO: Temp way to increase XP for debug purposes
            IconButton(onClick = { AddXp(viewModel, profile, 9900) }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }

        // XP bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.07f)
                .padding(horizontal = 16.dp)
        ) {
            XpProgressBar(profile)
        }

        // Apartment section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.33f)
                .background(color = MaterialTheme.colorScheme.background)
        ) {

        }

        // Summary of current wealth situation
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            HomePageSummary(viewModel)
        }

        // Recent trophies
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            RecentTrophiesSection(profile.achievements.takeLast(5).asReversed())
        }

        // Log expense button
        Button(
            onClick = { showBottomSheet = true },
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text("Log Expense")
        }
    }

    // Bottom sheet for adding new expense
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            LogExpenseSheet(
                viewModel = viewModel,
                onSave = { amount, category, note ->
                    viewModel.addExpense(amount, category, note)
                    showBottomSheet = false
                    viewModel.checkAchievements(profile, viewModel.totalNetWorth.value)
                },
                onDismiss = { showBottomSheet = false }
            )
        }
    }
}

// TODO: TEMP function to add XP
fun AddXp(viewModel: MainViewModel, profile: Profile, amount: Int){
    viewModel.updateProfile(profile, profile.name, (profile.xpAmount + amount), profile.xpLevel,
        profile.achievements, profile.dailyStreak, profile.lastLogin)
}