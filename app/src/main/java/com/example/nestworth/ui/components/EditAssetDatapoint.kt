package com.example.nestworth.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.nestworth.Repository.model.AssetDatapoint
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditAssetDatapoint(
    dataPoint: AssetDatapoint,
    onDismiss: () -> Unit,
    onConfirm: (value: Double, liability: Double, date: LocalDate) -> Unit,
    onDelete: () -> Unit
) {
    var assetValue by remember { mutableStateOf(dataPoint.value.toString()) }
    var assetLiability by remember { mutableStateOf(dataPoint.liability.toString()) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    val displayDate = remember(dataPoint.date) {
        Instant.ofEpochMilli(dataPoint.date)
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
                Text("Edit datapoint")
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
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = assetValue,
                    onValueChange = { assetValue = it },
                    label = { Text("Value") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                OutlinedTextField(
                    value = assetLiability,
                    onValueChange = { assetLiability = it },
                    label = { Text("Liability") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
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
                    val value = assetValue.toDoubleOrNull() ?: 0.0
                    val liability = assetLiability.toDoubleOrNull() ?: 0.0
                    onConfirm(value, liability, parsedDate ?: LocalDate.now())
                },
                enabled = assetValue.isNotEmpty() && assetLiability.isNotEmpty() && dateIsValid
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
            title = "Delete datapoint?",
            message = "This action cannot be undone."
        )
    }
}