package com.example.nestworth.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
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
fun EditExpenseDialog(
    viewModel: MainViewModel,
    onSave: (Double, String, String, date: LocalDate) -> Unit,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    selectedExpense: Expense
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val categories by viewModel.allExpenseCategories.collectAsState()
    var selectedCategory by remember(selectedExpense, categories.isEmpty()) {
        mutableStateOf<ExpenseCategory?>(
            categories.find { it.name == selectedExpense.category }
        )
    }
    var amount by remember(selectedExpense) {
        mutableStateOf(selectedExpense.amount.toString() ?: "")
    }
    var note by remember(selectedExpense) {
        mutableStateOf(selectedExpense.note ?: "")
    }
    var showAddCategory by remember { mutableStateOf(false) }

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
    val dateIsValid = parsedDate != null

    // Main dialog for editing datapoint
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val title = if (selectedExpense.isIncome) "Income" else "Expense"
                Text("Edit $title")
                IconButton(onClick = { showDeleteConfirm = true }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!selectedExpense.isIncome) {
                    // Horizontal scrollable category chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories.sortedByDescending{ it.id }) { category ->
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
                }

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount €") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Note (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = dateInput,
                    onValueChange = { dateInput = it },
                    label = { Text("Date (yyyy-MM-dd)") },
                    placeholder = { Text(LocalDate.now().format(formatter)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    isError = dateInput.isNotBlank() && !dateIsValid,
                    supportingText = {
                        if (dateInput.isNotBlank() && !dateIsValid)
                            Text("Invalid date — use yyyy-MM-dd")
                        else if (dateInput.isBlank())
                            Text("Leave empty to use today")
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        amount.toDoubleOrNull() ?: 0.0,
                        selectedCategory?.name ?: "",
                        note,
                        parsedDate ?: LocalDate.now()
                    )
                },
                enabled = (selectedCategory != null || selectedExpense.isIncome) && amount.isNotEmpty() && dateIsValid
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )

    // Delete confirmation dialog
    if (showDeleteConfirm) {
        ConfirmationDialog(
            onDismiss = { showDeleteConfirm = false },
            onConfirm = onDelete,
            title = "Delete expense?",
            message = "This action cannot be undone."
        )
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