package com.example.nestworth.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(
    topAssets: List<AssetEquity>,
    totalNetWorth: Float
)
{
    // Full circle = net wealth
    // Each asset will have portion, showing how much of NW is contributed by that asset equity
    val stroke = 20.dp
    val othersColor = Color(0xFF7A6A5A)
    val graphColors = listOf(
        Color(0xFFE8C96E), // #1 — Gold
        Color(0xFFC27B5A), // #2 — Terracotta
        Color(0xFF6E8FA3), // #3 — Muted blue
    )

    Canvas(modifier = Modifier
        .size(150.dp)){
        // Draw circle progress bars
        drawArc(
            color = othersColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(stroke.toPx(), cap = StrokeCap.Round)
        )

        var startAngle = -90f
        topAssets.forEachIndexed { index, item ->
            val newAngle = (item.equity.toFloat()/totalNetWorth)*360f
            drawArc(
                color = graphColors[index],
                startAngle = startAngle,
                sweepAngle = newAngle,
                useCenter = false,
                style = Stroke(stroke.toPx(), cap = StrokeCap.Round)
            )
            startAngle += newAngle
        }
    }
}