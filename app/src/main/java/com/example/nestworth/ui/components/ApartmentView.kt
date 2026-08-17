package com.example.nestworth.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestworth.Repository.model.Profile
import com.example.nestworth.apartment.ApartmentCatalog
import com.example.nestworth.apartment.Category
import com.example.nestworth.ui.utils.FormatMoney
import com.example.nestworth.R

@Composable
fun ApartmentView(
    profile: Profile,
    totalNW: Double,
)
{
    val items = ApartmentCatalog.getCurrentItems(profile.xpLevel)
    val tvItem = items.find { it.category == Category.TV }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        items.forEach { item ->
            Image(
                painter = painterResource(item.asset),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .offset(
                        x = maxWidth * item.layout.x,
                        y = maxHeight * item.layout.y
                    )
                    .width(maxWidth * item.layout.width)
                    .height(maxHeight * item.layout.height)
            )
        }

        // Net wealth displayed on TV
        if (tvItem != null) {
            // TODO: Find some nice font for the TV
            //val tvFont = FontFamily(
            //    Font(R.font.cairopixel)
            //)

            Box(
                modifier = Modifier
                    .offset(
                        x = maxWidth * tvItem.layout.x,
                        y = maxHeight * tvItem.layout.y
                    )
                    .width(maxWidth * tvItem.layout.width)
                    .height(maxHeight * tvItem.layout.height),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .background(
                            color = Color(0xCC080F10),
                            shape = RoundedCornerShape(4.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = FormatMoney(totalNW),
                        color = colorResource(id = R.color.gain_green),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color(0xAA4A7C59),
                                blurRadius = 3f
                            )
                        )
                    )
                }
            }
        }
    }
}
