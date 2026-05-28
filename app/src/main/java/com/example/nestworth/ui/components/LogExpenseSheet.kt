package com.example.nestworth.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.lazy.items
import com.example.nestworth.Repository.model.Expense
import com.example.nestworth.Repository.model.ExpenseCategory
import com.example.nestworth.ui.viewmodel.MainViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogExpenseSheet(
    viewModel: MainViewModel,
    onSave: (Double, String, String) -> Unit,
    onDismiss: () -> Unit,
    selectedExpense: Expense? = null
) {

    // TODO: Add text field here to edit date
    // Some DB changes needed as well
    // TODO: Add method to delete expense (some cool thrash can icon)
    val categories by viewModel.allExpenseCategories.collectAsState()
    var selectedCategory by remember(selectedExpense, categories.isEmpty()) {
        mutableStateOf<ExpenseCategory?>(
            categories.find { it.name == selectedExpense?.category }
        )
    }
    var amount by remember(selectedExpense) {
        mutableStateOf(selectedExpense?.amount?.toString() ?: "")
    }
    var note by remember(selectedExpense) {
        mutableStateOf(selectedExpense?.note ?: "")
    }
    var showAddCategory by remember { mutableStateOf(false) }

    var title = "Log Expense"
    if (selectedExpense != null) {
        val displayDate = remember(selectedExpense.date) {
            Instant.ofEpochMilli(selectedExpense.date)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }
        var dateInput by remember { mutableStateOf(displayDate) }

        val formatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }

        val parsedDate: LocalDate? = remember(dateInput) {
            if (dateInput.isBlank()) LocalDate.now()
            else try { LocalDate.parse(dateInput, formatter) } catch (e: DateTimeParseException) { null }
        }
        title = "Edit Expense"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Horizontal scrollable category chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text("${category.emoji}") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondary,
                        selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            }
            // Add category chip at the end
            item {
                FilterChip(
                    selected = false,
                    onClick = { showAddCategory = true },
                    label = { Text("+ Add") }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount €") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note (optional)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSave(
                    amount.toDoubleOrNull() ?: 0.0,
                    selectedCategory?.name ?: "",
                    note
                )
            },
            enabled = selectedCategory != null && amount.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    // Show dialog for adding new expense category
    if (showAddCategory) {
        AddExpenseCategoryDialog (
            onDismiss = { showAddCategory = false },
            onConfirm = { name, emoji ->
                viewModel.addExpenseCategory(name, emoji)
                showAddCategory = false
            }
        )
    }
}