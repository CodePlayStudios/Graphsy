package com.graphsy.compose.donut

import android.graphics.Path.Direction
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.graphsy.compose.common.DonutChartUtils
import com.graphsy.compose.config.DonutChartConfiguration
import com.graphsy.compose.models.DonutData
import com.graphsy.compose.models.DonutPathData
import com.graphsy.compose.models.DonutPathDataEntry


@Composable
fun DonutChart(
    vararg data: DonutData,
    modifier: Modifier = Modifier,
    configuration: DonutChartConfiguration = DonutChartConfiguration()
) {
    var usedWidth = 0f
    data.forEachIndexed { index, item ->
        DrawDonut(
            model = item,
            config = configuration,
            modifier = modifier,
            usedWidth = usedWidth
        )
        usedWidth += item.masterSlice.strokeWidth / 2
    }
}

@Composable
private fun DrawDonut(
    model: DonutData,
    config: DonutChartConfiguration,
    modifier: Modifier,
    usedWidth: Float
) {
    val masterProgress = model.masterSlice.circumferencePercentage
    val gapAngleDegrees = config.gapAngleDegrees.angle
    val gapWidthDegrees = config.gapWidthDegrees.angle
    val backgroundLineColor = model.masterSlice.color

    val wholeDonutAngle = 360f - gapWidthDegrees
    val masterSegmentAngle = wholeDonutAngle * masterProgress
    val startAngle = gapAngleDegrees + gapWidthDegrees / 2
    val strokeCap = StrokeCap.Butt

    val masterPathData = DonutPathDataEntry(
        color = backgroundLineColor,
        startAngle = startAngle,
        sweepAngle = masterSegmentAngle,
        strokeWidth = model.masterSlice.strokeWidth
    )
    val sectionsPathData = DonutChartUtils.calculatePathData(
        data = model,
        configuration = config
    )
    val entriesPathData = model.sections.mapIndexed { index, item ->
        DonutPathDataEntry(
            color = item.color,
            startAngle = sectionsPathData[index].startAngle,
            sweepAngle = sectionsPathData[index].sweepAngle,
            strokeWidth = model.masterSlice.strokeWidth
        )
    }

    val donutPathData = DonutPathData(masterPathData, entriesPathData)

    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }

    Canvas(modifier = modifier
        .padding(
            usedWidth.dp
        )
        .onSizeChanged { size ->
            chartWidth = size.width.toFloat()
            chartHeight = size.height.toFloat()
        }, onDraw = {
        drawDonutSegment(
            strokeCap,
            donutPathData.masterPathData,
            config.direction
        )
        donutPathData.entriesPathData.forEach { pathData ->
            drawDonutSegment(strokeCap, pathData, config.direction)
        }
    })
}

private fun DrawScope.drawDonutSegment(
    strokeCap: StrokeCap,
    data: DonutPathDataEntry,
    direction: Direction
) {
    val angle = if (direction == Direction.CCW) -1 else 1
    drawArc(
        color = data.color,
        startAngle = data.startAngle * angle,
        sweepAngle = data.sweepAngle * angle,
        useCenter = false,
        topLeft = Offset.Zero + Offset(
            x = data.strokeWidth / 2f,
            y = data.strokeWidth / 2f
        ),
        size = Size(
            size.width - data.strokeWidth,
            size.height - data.strokeWidth
        ),
        style = Stroke(
            width = data.strokeWidth,
            cap = strokeCap
        )
    )
}
