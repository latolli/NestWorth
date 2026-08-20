package com.example.nestworth.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestworth.Repository.settings.Currency
import com.example.nestworth.Repository.settings.ThemeMode
import com.example.nestworth.achievement.StartingStep
import com.example.nestworth.core.LocalAppSettings
import com.example.nestworth.core.TutorialEnum
import com.example.nestworth.ui.viewmodel.MainViewModel
import com.example.nestworth.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    mainViewModel: MainViewModel,
    settingsViewModel: SettingsViewModel,
    onBack: () -> Unit,
    openTutorial: (TutorialEnum) -> Unit
) {
    val settings = LocalAppSettings.current
    val currentProfile by mainViewModel.latestProfile.collectAsState()
    val profile = currentProfile ?: return  // local val, smart-cast works fine

    var currencyExpanded by remember { mutableStateOf(false) }
    var themeExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        // Top title bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier.weight(0.1f),
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(0.8f)
            )
            Box(modifier = Modifier.weight(0.1f))
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            thickness = 0.8.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            // Preferences
            SettingsSectionHeader("Preferences")
            SettingsGroup {
                Box {
                    SettingsValueRow(
                        label = "App theme",
                        value = settings.themeMode.name,
                        onClick = { themeExpanded = true }
                    )
                    DropdownMenu(
                        expanded = themeExpanded,
                        onDismissRequest = { themeExpanded = false }
                    ) {
                        ThemeMode.entries.forEach { themeMode ->
                            DropdownMenuItem(
                                text = { Text(themeMode.name) },
                                onClick = {
                                    settingsViewModel.setTheme(themeMode)
                                    themeExpanded = false
                                }
                            )
                        }
                    }
                }
                SettingsRowDivider()
                Box {
                    SettingsValueRow(
                        label = "Currency",
                        value = settings.currency.name,
                        onClick = { currencyExpanded = true }
                    )
                    DropdownMenu(
                        expanded = currencyExpanded,
                        onDismissRequest = { currencyExpanded = false }
                    ) {
                        Currency.entries.forEach { currency ->
                            if (currency != Currency.PERCENTAGE) {
                                DropdownMenuItem(
                                    text = { Text(currency.name) },
                                    onClick = {
                                        settingsViewModel.setCurrency(currency)
                                        currencyExpanded = false
                                        // Unlock achievement the first time currency is chosen
                                        if (profile.startingSteps and StartingStep.CURRENCY_SELECTED.mask == 0) {
                                            mainViewModel.updateProfile(
                                                profile,
                                                profile.name,
                                                profile.xpAmount,
                                                profile.xpLevel,
                                                profile.achievements,
                                                profile.dailyStreak,
                                                profile.lastLogin,
                                                startingSteps = profile.startingSteps or StartingStep.CURRENCY_SELECTED.mask
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Tutorials
            SettingsSectionHeader("Tutorials")
            SettingsGroup {
                SettingsActionRow(
                    label = "Home",
                    onClick = { openTutorial(TutorialEnum.HOME) }
                )
                SettingsRowDivider()
                SettingsActionRow(
                    label = "Assets",
                    onClick = { openTutorial(TutorialEnum.ASSETS) }
                )
                SettingsRowDivider()
                SettingsActionRow(
                    label = "Expenses / income",
                    onClick = { openTutorial(TutorialEnum.INCOME_EXPENSES) }
                )
            }

            // About
            SettingsSectionHeader("About")
            SettingsGroup {
                ListItem(
                    headlineContent = {
                        Text(
                            text = "Version",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    trailingContent = {
                        Text(
                            text = "0.1.0",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
                )
            }
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
        letterSpacing = 1.sp,
        modifier = Modifier.padding(start = 4.dp, top = 20.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsGroup(content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(content = content)
    }
}

@Composable
private fun SettingsRowDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.6.dp,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
    )
}

// A row whose value can be changed (theme, currency) — whole row is tappable,
// shows the current value plus a chevron to signal "opens a picker".
@Composable
private fun SettingsValueRow(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        },
        colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent),
        modifier = Modifier.clickable(onClick = onClick)
    )
}

// A row that triggers a navigation action (open tutorial) — whole row is
// tappable too, but styled distinctly from a value picker via a plain chevron.
@Composable
private fun SettingsActionRow(
    label: String,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Open",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent),
        modifier = Modifier.clickable(onClick = onClick)
    )
}