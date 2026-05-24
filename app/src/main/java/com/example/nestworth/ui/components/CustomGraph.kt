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
import androidx.compose.ui.graphics.PathEffect
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
) {
    val textMeasurer = rememberTextMeasurer()

    if (dataPoints.isEmpty()) return

    // --- 1. Compute equity range with margin ---
    val equities = dataPoints.map { it.value - it.liability }
    val rawMin = equities.min()
    val rawMax = equities.max()
    val rawRange = rawMax - rawMin

    // Use a flat absolute margin so both sides expand symmetrically
    val marginAbs = rawRange * 0.15
    val minEquityAdjusted = rawMin - marginAbs
    val maxEquityAdjusted = rawMax + marginAbs
    val totalEqRange = (maxEquityAdjusted - minEquityAdjusted).toFloat()

    // --- 2. Compute a "nice" step size using log10 ---
    fun niceStepSize(range: Double): Double {
        val roughStep = range / 6.0          // aim for ~6 gridlines
        val magnitude = Math.pow(10.0, Math.floor(Math.log10(roughStep)))
        val normalized = roughStep / magnitude
        return when {
            normalized < 1.5 -> 1.0
            normalized < 3.5 -> 2.0
            normalized < 7.5 -> 5.0
            else             -> 10.0
        } * magnitude
    }

    val stepSize = niceStepSize(rawRange.coerceAtLeast(1.0))

    // --- 3. Generate step values covering the full adjusted range ---
    //    Start from the first multiple of stepSize >= minEquityAdjusted
    val firstStep = Math.ceil(minEquityAdjusted / stepSize) * stepSize
    val steps = generateSequence(firstStep) { it + stepSize }
        .takeWhile { it <= maxEquityAdjusted + stepSize * 0.01 }
        .toList()

    // --- 4. Date range ---
    val minDate = dataPoints.minOf { it.date }
    val maxDate = dataPoints.maxOf { it.date }
    val dateRange = (maxDate - minDate).toFloat().coerceAtLeast(1f)

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(BackgroundLight)
    ) {
        val width  = size.width
        val height = size.height

        // --- 5. Reserve left padding for Y-axis labels ---
        val labelPadding = 46.dp.toPx()
        val graphWidth   = width - labelPadding
        val graphLeft    = labelPadding

        // Helper: equity value → canvas Y
        fun equityToY(eq: Double) =
            height - ((eq - minEquityAdjusted).toFloat() / totalEqRange) * height

        // Helper: datapoint → canvas Offset (within the padded graph area)
        val points = dataPoints.map { dp ->
            val eq = dp.value - dp.liability
            Offset(
                x = graphLeft + ((dp.date - minDate).toFloat() / dateRange) * graphWidth,
                y = equityToY(eq)
            )
        }

        // --- 6. Draw horizontal grid lines and Y-axis labels ---
        for (step in steps) {
            val yPos = equityToY(step)

            // Skip lines outside the visible canvas
            if (yPos < 0 || yPos > height) continue

            // Grid line
            drawLine(
                color = DarkBrown.copy(alpha = 0.12f),
                start = Offset(graphLeft, yPos),
                end   = Offset(width, yPos),
                strokeWidth = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f))
            )

            // Label — abbreviate large numbers (>=100k), drop decimals for integers, keep 2dp otherwise
            val label = when {
                Math.abs(step) >= 100_000 -> {
                    val k = step / 1_000.0
                    if (k == Math.floor(k))
                        String.format(Locale.getDefault(), "%.0fk", k)
                    else
                        String.format(Locale.getDefault(), "%.1fk", k)
                }
                step == Math.floor(step) ->
                    String.format(Locale.getDefault(), "%.0f", step)
                else ->
                    String.format(Locale.getDefault(), "%.2f", step)
            }

            val textResult = textMeasurer.measure(
                text  = label,
                style = TextStyle(
                    color      = DarkBrown,
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            // Right-align labels against the graph edge
            drawText(
                textLayoutResult = textResult,
                topLeft = Offset(
                    x = graphLeft - textResult.size.width - 6.dp.toPx(),
                    y = yPos - textResult.size.height / 2f
                )
            )
        }

        // --- 7. Zero line (only when range crosses zero) ---
        if (rawMin < 0 && rawMax > 0) {
            val zeroY = equityToY(0.0)
            drawLine(
                color       = DarkBrown.copy(alpha = 0.35f),
                start       = Offset(graphLeft, zeroY),
                end         = Offset(width, zeroY),
                strokeWidth = 1.5.dp.toPx()
            )
        }

        // --- 8. Gradient fill (split above/below zero) ---
        val zeroY = equityToY(0.0).coerceIn(0f, height)

        val fillPath = Path().apply {
            moveTo(points.first().x, zeroY)
            points.forEach { lineTo(it.x, it.y) }
            lineTo(points.last().x, zeroY)
            close()
        }
        drawPath(
            path  = fillPath,
            brush = Brush.verticalGradient(
                0f to GainGreen.copy(alpha = 0.45f),
                1f to Color.Transparent,
                startY = 0f,
                endY   = height
            )
        )

        // --- 9. Line on top ---
        for (i in 0 until points.size - 1) {
            drawLine(
                color       = GainGreen,
                start       = points[i],
                end         = points[i + 1],
                strokeWidth = 3.dp.toPx()
            )
        }
    }
}