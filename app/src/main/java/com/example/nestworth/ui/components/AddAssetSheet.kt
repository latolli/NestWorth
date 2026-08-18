package com.example.nestworth.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AddAssetSheet(
    onSave: (String, Double, Double) -> Unit,
    onDismiss: () -> Unit
) {
    var assetName by remember { mutableStateOf("") }
    var assetValue by remember { mutableStateOf("") }
    var assetLiability by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Add new asset", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = assetName,
            onValueChange = { assetName = it },
            label = { Text("Name of the asset") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = assetValue,
            onValueChange = { assetValue = it },
            label = { Text("Asset value €") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = assetLiability,
            onValueChange = { assetLiability = it },
            label = { Text("Liability €") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSave(
                    assetName,
                    assetValue.toDoubleOrNull() ?: 0.0,
                    assetLiability.toDoubleOrNull() ?: 0.0
                )
            },
            enabled = assetValue.isNotEmpty() && assetName.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}