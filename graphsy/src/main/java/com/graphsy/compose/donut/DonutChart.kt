package com.graphsy.compose.donut

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


@Composable
fun DonutChart(
    vararg data: DonutData,
    modifier: Modifier = Modifier,
    configuration: DonutChartConfiguration = DonutChartConfiguration()
) {
    DrawDonut(
        model = data.first(),
        config = configuration,
        modifier = modifier
    )
}


@Composable
private fun DrawDonut(
    model: DonutData,
    config: DonutChartConfiguration,
    modifier: Modifier
) {

    val masterProgress = model.masterSlice.circumferencePercentage
    val gapAngleDegrees = config.gapAngleDegrees.angle
    val gapWidthDegrees = config.gapWidthDegrees.angle
    val backgroundLineColor = config.backgroundLineColor

    val wholeDonutAngle = 360f - gapWidthDegrees
    val masterSegmentAngle = wholeDonutAngle * masterProgress
    val startAngle = gapAngleDegrees + gapWidthDegrees / 2
    val strokeCap = StrokeCap.Butt

    val masterPathData = DonutPathDataEntry(
        color = backgroundLineColor,
        startAngle = startAngle,
        sweepAngle = masterSegmentAngle
    )
    val sectionsPathData = DonutChartUtils.calculatePathData(
        data = model,
        configuration = config
    )
    val entriesPathData = model.sections.mapIndexed {index, item->
        DonutPathDataEntry(
            color = item.color,
            startAngle = sectionsPathData[index].startAngle,
            sweepAngle = sectionsPathData[index].sweepAngle
        )
    }

    val donutPathData = DonutPathData(masterPathData, entriesPathData)

    Canvas(modifier = modifier, onDraw = {
        drawDonutSegment(
            config.strokeWidth,
            strokeCap,
            donutPathData.masterPathData
        )
        donutPathData.entriesPathData.forEach { pathData ->
            drawDonutSegment(config.strokeWidth, strokeCap, pathData)
        }
    })
}


private fun DrawScope.drawDonutSegment(
    strokeWidth: Float,
    strokeCap: StrokeCap,
    data: DonutPathDataEntry
) {
    drawArc(
        color = data.color,
        startAngle = data.startAngle,
        sweepAngle = data.sweepAngle,
        useCenter = false,
        topLeft = Offset.Zero + Offset(
            x = strokeWidth / 2f,
            y = strokeWidth / 2f
        ),
        size = Size(
            size.width - strokeWidth,
            size.height - strokeWidth
        ),
        style = Stroke(
            width = strokeWidth,
            cap = strokeCap
        )
    )
}
