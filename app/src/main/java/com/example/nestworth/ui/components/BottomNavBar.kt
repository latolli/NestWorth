package com.example.nestworth.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlin.collections.contains

@Composable
fun BottomNavBar(
    navController: NavController
)
{
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    // Define which routes show the bottom bar
    val showBottomBar = currentRoute in listOf("home", "assets", "eventHistory", "settings") ||
            currentRoute?.startsWith("asset/") == true ||
            currentRoute?.startsWith("profile/") == true ||
            currentRoute?.startsWith("tutorials/") == true

    if (showBottomBar) {
        NavigationBar (
            containerColor = MaterialTheme.colorScheme.background
        ){
            NavigationBarItem(
                selected = currentRoute == "home",
                onClick = { navController.navigate("home") },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
            NavigationBarItem(
                selected = currentRoute == "assets",
                onClick = { navController.navigate("assets") },
                icon = { Icon(Icons.Default.AccountBox, contentDescription = "Assets") },
                label = { Text("Assets") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
            NavigationBarItem(
                selected = currentRoute == "eventHistory",
                onClick = { navController.navigate("eventHistory") },
                icon = { Icon(Icons.Default.Refresh, contentDescription = "Event History") },
                label = { Text("History") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}