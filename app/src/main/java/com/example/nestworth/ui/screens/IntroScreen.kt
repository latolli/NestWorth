package com.example.nestworth.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nestworth.R

@Composable
fun IntroScreen(
    onSignUp: () -> Unit = {}
) {
    Column (
        modifier = Modifier.fillMaxSize().padding(76.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth().aspectRatio(1f)
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.nestworth_logo_foreground),
                contentDescription = "Profile picture",
                modifier = Modifier.fillMaxWidth().clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Button(
            onClick = onSignUp,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Get Started")
        }
    }
}
