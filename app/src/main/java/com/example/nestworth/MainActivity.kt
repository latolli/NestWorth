package com.example.nestworth

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nestworth.ui.screens.HomeScreen
import com.example.nestworth.ui.screens.IntroScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nestworth.ui.components.BottomNavBar
import com.example.nestworth.ui.screens.AssetHistoryScreen
import com.example.nestworth.ui.screens.AssetInfoScreen
import com.example.nestworth.ui.screens.AssetsScreen
import com.example.nestworth.ui.screens.ExpenseHistoryScreen
import com.example.nestworth.ui.screens.ProfileScreen
import com.example.nestworth.ui.screens.SignUpScreen
import com.example.nestworth.ui.viewmodel.MainViewModel
import com.yourname.nestworth.ui.theme.NestWorthTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NestWorthTheme {
                val app = application as NestWorthApp
                val viewModel: MainViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return MainViewModel(app.database) as T
                        }
                    }
                )
                AppNavigation(viewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val allProfiles by viewModel.allProfiles.collectAsState()

    if (allProfiles == null) {
        return
    }

    val startScreen = if (allProfiles!!.isEmpty()) "intro" else "home"

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("intro") {
                IntroScreen(
                    onSignUp = { navController.navigate("signUp") }
                )
            }
            composable("signUp") {
                SignUpScreen(
                    onConfirm = {
                        navController.navigate("home") {
                            popUpTo("intro") { inclusive = true }
                        }
                    },
                    onBack = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onProfileClick = { profileId ->
                        navController.navigate("profile/$profileId")
                    }
                )
            }
            composable("profile/{profileId}") {
                val profileId = it.arguments?.getString("profileId")?.toIntOrNull()
                if (profileId != null) {
                    ProfileScreen(
                        viewModel = viewModel,
                        profileId = profileId,
                        onBack = { navController.navigate("home") }
                    )
                }
            }
            composable("assets") {
                AssetsScreen(
                    viewModel = viewModel,
                    onAssetClick = { asset -> navController.navigate("asset/${asset.id}") }
                )
            }
            composable("asset/{assetId}") { backStackEntry ->
                val assetId = backStackEntry.arguments?.getString("assetId")?.toIntOrNull()
                if (assetId != null) {
                    AssetInfoScreen(
                        viewModel = viewModel,
                        assetId = assetId,
                        onBack = { navController.navigate("assets") },
                        onEditHistory = { navController.navigate("asset/${assetId}/history") }
                    )
                }
            }
            composable("asset/{assetId}/history") { backStackEntry ->
                val assetId = backStackEntry.arguments?.getString("assetId")?.toIntOrNull()
                if (assetId != null) {
                    AssetHistoryScreen(
                        viewModel = viewModel,
                        assetId = assetId,
                        onBack = { navController.navigate("asset/${assetId}") }
                    )
                }
            }
            composable("expenseHistory"){
                ExpenseHistoryScreen(
                    viewModel = viewModel)
            }
        }
    }
}