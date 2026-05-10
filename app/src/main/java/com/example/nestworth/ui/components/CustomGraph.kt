package com.example.nestworth.ui.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestworth.Repository.model.AssetDatapoint
import com.yourname.nestworth.ui.theme.BackgroundLight
import com.yourname.nestworth.ui.theme.DarkBrown
import com.yourname.nestworth.ui.theme.GainGreen
import java.util.Locale

@Composable
fun CustomGraph(
    dataPoints: List<AssetDatapoint>,
)
{
    /*
    TODO:
    - Make graph look nicer
    - Handle negative inputs somehow
     */
    val textMeasurer = rememberTextMeasurer()

    // Validity check
    if (dataPoints.isEmpty()) return

    // Calculate min and max Y values
    val margin = 0.15
    val minEquity = dataPoints.minOf { it.value - it.liability }
    val minEquityAdjusted =  if (minEquity > 0)
        (1 - margin) * minEquity
        else (1 + margin) * minEquity
    val maxEquity = dataPoints.maxOf { it.value - it.liability }
    val maxEquityAdjusted = if (maxEquity > 0)
        (1 + margin) * maxEquity
        else (1 - margin) * maxEquity

    // Calculate steps sizes for Y axis
    val totalEqRange = (maxEquityAdjusted - minEquityAdjusted).toFloat()
    var stepSize = 1
    var increment = 10
    for (i in 0 until 10) {
        stepSize *= increment
        if (totalEqRange / stepSize > 100){
            increment = 10
            continue
        }
        else if (totalEqRange / stepSize > 10){
            increment = 2
            continue
        }
        else {
            break
        }
    }

    // Find min and max dates
    val minDate = dataPoints.minOf { it.date }
    val maxDate = dataPoints.maxOf { it.date }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(8.dp))
        .background(BackgroundLight)) {
        // Normalize data
        val width = size.width
        val height = size.height
        val points = dataPoints.map { dp ->
            val equity = dp.value - dp.liability  // Double - Double = Double
            Offset(
                x = ((dp.date - minDate).toFloat() / (maxDate - minDate).toFloat()) * width,
                y = height - ((equity - minEquityAdjusted).toFloat() / totalEqRange) * height
            )
        }

        // Straight-line fill path
        val fillPath = Path().apply {
            moveTo(points.first().x, height)   // start at bottom-left
            points.forEach { lineTo(it.x, it.y) }  // straight lines through all points
            lineTo(points.last().x, height)    // back down to bottom-right
            close()
        }

        // Draw fill
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(GainGreen.copy(alpha = 0.4f), Color.Transparent)
            )
        )

        // Draw straight lines on top
        for (i in 0 until points.size - 1) {
            drawLine(
                color = GainGreen,
                start = points[i],
                end = points[i + 1],
                strokeWidth = 3.dp.toPx()
            )
        }

        // Draw steps to Y-axis
        val stepCount = totalEqRange / stepSize
        val firstStep = if (stepSize > minEquity) stepSize else stepSize + stepSize
        for (i in 0 until stepCount.toInt()) {
            val stepValue = (firstStep + i * stepSize).toFloat();
            val textLayoutResult = textMeasurer.measure(
                text = String.format(Locale.getDefault(),"%.0f", stepValue),
                style = TextStyle(
                    color = DarkBrown,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            val yPos = height - ((stepValue - minEquityAdjusted).toFloat() / totalEqRange) * height
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(x = 4.dp.toPx(), y = yPos - 8.dp.toPx())
            )

        }
    }
}