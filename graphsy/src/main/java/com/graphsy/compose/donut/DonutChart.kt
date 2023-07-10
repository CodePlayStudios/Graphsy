package com.graphsy.compose.donut

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

    data.toList().forEachIndexed { index, item ->
        DrawDonut(
            model = item,
            config = configuration,
            modifier = modifier,
            donutIndex = index
        )
    }
}

@Composable
private fun DrawDonut(
    model: DonutData,
    config: DonutChartConfiguration,
    modifier: Modifier,
    donutIndex: Int
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
        sweepAngle = masterSegmentAngle
    )
    val sectionsPathData = DonutChartUtils.calculatePathData(
        data = model,
        configuration = config
    )
    val entriesPathData = model.sections.mapIndexed { index, item ->
        DonutPathDataEntry(
            color = item.color,
            startAngle = sectionsPathData[index].startAngle,
            sweepAngle = sectionsPathData[index].sweepAngle
        )
    }

    val donutPathData = DonutPathData(masterPathData, entriesPathData)

    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }

    Canvas(modifier = modifier
        .padding(
            config.innerDonutSpacing * donutIndex
        )
        .onSizeChanged { size ->
            chartWidth = size.width.toFloat()
            chartHeight = size.height.toFloat()
        }, onDraw = {
        val width = config.strokeWidth - config.childDonutWidthFactor * donutIndex
        drawDonutSegment(
            width,
            strokeCap,
            donutPathData.masterPathData
        )
        donutPathData.entriesPathData.forEach { pathData ->
            drawDonutSegment(width, strokeCap, pathData)
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
