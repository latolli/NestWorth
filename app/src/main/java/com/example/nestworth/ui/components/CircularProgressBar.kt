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
    Canvas(modifier = Modifier
        .size(150.dp)){
        // Draw circle progress bars
        drawArc(
            color = Color.DarkGray,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(stroke.toPx(), cap = StrokeCap.Round)
        )

        val graphColors: List<Color> = listOf(
            Color.Green,
            Color.Red,
            Color.Blue
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