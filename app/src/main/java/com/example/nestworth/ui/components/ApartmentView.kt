package com.example.nestworth.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.nestworth.Repository.model.Profile
import com.example.nestworth.apartment.ApartmentCatalog

@Composable
fun ApartmentView(
    profile: Profile,
    totalNW: Double,
)
{
    val items = ApartmentCatalog.getCurrentItems(profile.xpLevel)

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        items.forEach { item ->
            Image(
                painter = painterResource(item.asset),
                contentDescription = null,
                modifier = Modifier
                    .offset(
                        x = maxWidth * item.layout.x,
                        y = maxHeight * item.layout.y
                    )
                    .width(maxWidth * item.layout.width)
                    .height(maxHeight * item.layout.height)
            )
        }
    }
}
