package com.example.nestworth.ui.screens

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nestworth.ui.components.XpProgressBar
import com.example.nestworth.ui.viewmodel.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.nestworth.ui.components.HomePageSummary
import com.example.nestworth.ui.components.LogExpenseSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

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
            Icon(Icons.Default.Person, contentDescription = "Profile")
            Text("NestWorth",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface)
            Icon(Icons.Default.Settings, contentDescription = "Settings")
        }

        // XP bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.07f)
                .padding(horizontal = 16.dp)
        ) {
            XpProgressBar()
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
                },
                onDismiss = { showBottomSheet = false }
            )
        }
    }
}