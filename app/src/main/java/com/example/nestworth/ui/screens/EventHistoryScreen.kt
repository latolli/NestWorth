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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nestworth.R
import com.example.nestworth.Repository.model.Expense
import com.example.nestworth.ui.components.EditExpenseDialog
import com.example.nestworth.ui.utils.FormatMoney
import com.example.nestworth.ui.viewmodel.MainViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventHistoryScreen(viewModel: MainViewModel)
{
    // TODO: Add income events here as well and make combined list ordered by time
    val allExpenses = viewModel.allExpenses.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedExpense by remember { mutableStateOf<Expense?>(null) }
    val currentProfile by viewModel.latestProfile.collectAsState()
    val profile = currentProfile ?: return  // local val, smart-cast works fine

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        // Top title bar
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .weight(0.10f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            // Asset name
            Text(
                text = "Event History",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            thickness = 0.8.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )

        if (allExpenses.value.isEmpty())
        {
            Box(modifier = Modifier.fillMaxWidth().weight(0.9f), contentAlignment = Alignment.Center)
            {
                Text(text = "No data to display",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center)
            }
        }
        else{
            // List of expenses
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp)
                    .weight(0.9f)
            ) {
                items(allExpenses.value) { expense ->
                    Card(
                        onClick = { showEditDialog = true
                            selectedExpense = expense },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        val isIncome = expense.isIncome
                        val displayCategory = when {
                            isIncome -> "💰"
                            else -> expense.category
                        }
                        val colorRes = if (isIncome){
                            colorResource(id = R.color.gain_green)
                        } else colorResource(id = R.color.loss_red)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Display data
                            Box(modifier = Modifier.weight(0.33f), contentAlignment = Alignment.CenterStart){
                                Text(text = displayCategory,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Start)
                            }
                            Box(modifier = Modifier.weight(0.33f), contentAlignment = Alignment.Center){
                                // Convert date to something readable
                                val displayDate = remember(expense.date) {
                                    Instant.ofEpochMilli(expense.date)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                                }
                                Text(
                                    text = displayDate,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorRes, //MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Box(modifier = Modifier.weight(0.33f), contentAlignment = Alignment.CenterEnd){
                                Text(text = FormatMoney(expense.amount),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorRes, //MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        thickness = 0.8.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                }
            }
        }
    }

    // Dialog for editing expense
    if (showEditDialog) {
        EditExpenseDialog(
            viewModel = viewModel,
            onSave = { amount, category, note, date ->
                viewModel.updateExpense(selectedExpense!!, amount, category, note, date)
                showEditDialog = false
                selectedExpense = null
                viewModel.checkAchievements(profile, viewModel.totalNetWorth.value)
            },
            onDismiss = { showEditDialog = false
                selectedExpense = null },
            onDelete = { viewModel.deleteExpense(selectedExpense!!); showEditDialog = false},
            selectedExpense = selectedExpense!!
        )
    }
}