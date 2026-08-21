package com.example.nestworth.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nestworth.R
import com.example.nestworth.core.LocalAppSettings
import com.example.nestworth.core.formatMoney

@Composable
fun CircularProgressBar(
    topAssets: List<AssetEquity>,
    totalNetWorth: Float,
    timeRangeNWGrowth: Double
)
{
    // Full circle = net wealth
    // Each asset will have portion, showing how much of NW is contributed by that asset equity
    val settings = LocalAppSettings.current
    val stroke = 20.dp
    val textMeasurer = rememberTextMeasurer()

    val graphColors = listOf(
        colorResource(id = R.color.graph_gold),
        colorResource(id = R.color.graph_terracotta),
        colorResource(id = R.color.graph_muted_blue),
        MaterialTheme.colorScheme.onSurface)

    // Growth text styling
    val changColorRes = if (timeRangeNWGrowth >= 0){
        colorResource(id = R.color.gain_green)
    } else colorResource(id = R.color.loss_red)
    val addPlusSign = if (timeRangeNWGrowth > 0) "+" else ""

    Canvas(modifier = Modifier
        .size(150.dp)){
        // Draw circle progress bars
        drawArc(
            color = graphColors.last(),
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

        // Growth text
        val textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = changColorRes,
            textAlign = TextAlign.Center
        )

        val textLayout = textMeasurer.measure(
            text = "${addPlusSign}${formatMoney(timeRangeNWGrowth, settings.currency)}",
            style = textStyle
        )

        drawText(
            textLayoutResult = textLayout,
            topLeft = Offset(
                x = (size.width - textLayout.size.width) / 2f,
                y = (size.height - textLayout.size.height) / 2f
            )
        )
    }
}