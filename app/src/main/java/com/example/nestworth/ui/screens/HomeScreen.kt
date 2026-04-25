package com.example.nestworth.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onAddExpenseClick: () -> Unit = {}
) {
    Button(onClick = onAddExpenseClick) {
        Text("Add Expense")
    }
}