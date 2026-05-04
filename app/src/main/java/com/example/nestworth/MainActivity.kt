package com.example.nestworth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.example.nestworth.ui.screens.AssetInfoScreen
import com.example.nestworth.ui.screens.AssetsScreen
import com.example.nestworth.ui.viewmodel.MainViewModel
import com.yourname.nestworth.ui.theme.NestWorthTheme

class MainActivity : ComponentActivity() {
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

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "intro",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("intro") {
                IntroScreen(
                    onStartClick = { navController.navigate("home") }
                )
            }
            composable("home") {
                HomeScreen(
                    viewModel = viewModel
                )
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
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}