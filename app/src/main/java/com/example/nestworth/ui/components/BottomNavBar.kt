package com.example.nestworth.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    val showBottomBar = currentRoute in listOf("home", "assets")

    if (showBottomBar) {
        NavigationBar {
            NavigationBarItem(
                selected = currentRoute == "home",
                onClick = { navController.navigate("home") },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") }
            )
            NavigationBarItem(
                selected = currentRoute == "assets",
                onClick = { navController.navigate("assets") },
                icon = { Icon(Icons.Default.AccountBox, contentDescription = "Assets") },
                label = { Text("Assets") }
            )
        }
    }
}