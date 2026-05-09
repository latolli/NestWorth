package com.example.nestworth.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.nestworth.Repository.model.Asset
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddAssetDatapoint(
    asset: Asset,
    onDismiss: () -> Unit,
    onConfirm: (value: Double, liability: Double, date: LocalDate) -> Unit
) {
    var assetValue by remember { mutableStateOf("") }
    var assetLiability by remember { mutableStateOf("") }
    var dateInput by remember { mutableStateOf("") }

    val formatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }

    val parsedDate: LocalDate? = remember(dateInput) {
        if (dateInput.isBlank()) LocalDate.now()
        else try { LocalDate.parse(dateInput, formatter) } catch (e: DateTimeParseException) { null }
    }
    val dateIsValid = parsedDate != null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add new data for ${asset.name}") },
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
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}