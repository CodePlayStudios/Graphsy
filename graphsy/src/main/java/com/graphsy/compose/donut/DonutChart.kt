package com.graphsy.compose.donut

import android.graphics.Path.Direction
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.graphsy.compose.common.DonutChartUtils
import com.graphsy.compose.config.DonutChartConfiguration
import com.graphsy.compose.models.DonutData
import com.graphsy.compose.models.DonutPathData
import com.graphsy.compose.models.DonutPathDataEntry
import kotlin.math.min


@Composable
fun DonutChart(
    data: List<DonutData>,
    modifier: Modifier = Modifier,
    configuration: DonutChartConfiguration = DonutChartConfiguration()
) {
    Canvas(modifier = modifier) {
        data.forEachIndexed { index, item ->
            val donutPathData = pathData(
                item,
                configuration
            )
            val size = Size(
                min(size.width / index.inc(), size.width - item.masterSlice.strokeWidth),
                min(size.height / index.inc(), size.height - item.masterSlice.strokeWidth),
            )
            val offset = Offset.Zero + Offset(
                x = (this.size.width - size.width).div(2f),
                y = (this.size.height - size.height).div(2f)
            )
            drawDonutSegment(
                donutPathData.masterPathData,
                configuration.direction,
                offset,
                size
            )
            donutPathData.entriesPathData.forEach { pathData ->
                drawDonutSegment(pathData, configuration.direction, offset, size)
            }
        }
    }
}

private fun DrawScope.drawDonutSegment(
    data: DonutPathDataEntry,
    direction: Direction,
    offset: Offset,
    size: Size
) {
    val factor = if (direction == Direction.CCW) -1 else 1

    drawArc(
        color = data.color,
        startAngle = data.startAngle * factor,
        sweepAngle = data.sweepAngle * factor,
        useCenter = false,
        topLeft = offset,
        size = size,
        style = Stroke(
            width = data.strokeWidth,
            cap = StrokeCap.Butt
        )
    )
}

private fun pathData(
    model: DonutData,
    config: DonutChartConfiguration
): DonutPathData {
    val masterProgress = model.masterSlice.circumferencePercentage
    val gapAngleDegrees = config.gapAngleDegrees.angle
    val gapWidthDegrees = config.gapWidthDegrees.angle
    val backgroundLineColor = model.masterSlice.color

    val wholeDonutAngle = 360f - gapWidthDegrees
    val masterSegmentAngle = wholeDonutAngle * masterProgress
    val startAngle = gapAngleDegrees + gapWidthDegrees / 2

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

    return DonutPathData(masterPathData, entriesPathData)
}

