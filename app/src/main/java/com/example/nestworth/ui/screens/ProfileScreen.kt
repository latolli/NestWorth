package com.example.nestworth.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nestworth.R
import com.example.nestworth.ui.components.EditProfileDialog
import com.example.nestworth.ui.viewmodel.MainViewModel

@Composable
fun ProfileScreen(
    viewModel: MainViewModel,
    profileId: Int,
    onBack: () -> Unit,
    onProfileDelete: () -> Unit) {
    // TODO: add edit option for name and image

    val allProfiles by viewModel.allProfiles.collectAsState()
    val profile = (allProfiles?.find { it.id == profileId })?: return   // null check and smart-cast
    var showEditDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top info bar
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(0.1f)
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically) {
            // Back button
            IconButton(
                modifier = Modifier.weight(0.1f),
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Add data point",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            // Profile name
            Text(
                modifier = Modifier.weight(0.8f),
                text = profile.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            // Edit button
            IconButton(
                modifier = Modifier.weight(0.1f),
                onClick = { showEditDialog = true },
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Delete asset",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Profile picture
        Image(
            painter = painterResource(id = R.drawable.trump_official_portrait),
            contentDescription = "Profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .weight(0.2f)
        )

        // Name / Level / XP — left aligned within the column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .padding(top = 48.dp)
                .weight(0.15f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Level: ${profile.xpLevel}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "XP: ${profile.xpAmount} / 5000",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Trophies section — give it a fixed or weighted height instead of fillMaxSize
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .weight(0.55f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Achievements",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                thickness = 0.8.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            )
            TrophyGrid()
        }
    }

    // Dialog for editing profile
    if (showEditDialog) {
        EditProfileDialog (
            profile = profile,
            onConfirm = { name ->
                viewModel.updateProfile(profile, name, profile.xpAmount, profile.xpLevel, profile.achievements)
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false },
            onDelete = { viewModel.deleteProfile(profile); showEditDialog = false; onProfileDelete() },
        )
    }
}

@Composable
fun TrophyGrid() {
    val columns = 5
    val rowsCount = 20

    // Generate once and remember, so it doesn't reshuffle on recomposition
    val iconGrid: List<List<ImageVector>> = remember {
        val iconPool = listOf(
            Icons.Default.Star,
            Icons.Default.Face,
            Icons.Default.Lock,
            Icons.Default.Info
        )
        List(rowsCount) {
            List(columns) { iconPool.random() }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(iconGrid) { rowIcons ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (icon in rowIcons) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Trophy icon",
                        modifier = Modifier
                            .padding(4.dp)
                            .size(32.dp)
                    )
                }
            }
        }
    }
}