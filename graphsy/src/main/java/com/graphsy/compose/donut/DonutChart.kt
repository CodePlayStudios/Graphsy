package com.graphsy.compose.donut

import android.graphics.Path.Direction
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.graphsy.compose.animation.simpleChartAnimation
import com.graphsy.compose.common.DonutChartUtils
import com.graphsy.compose.config.DonutChartConfiguration
import com.graphsy.compose.models.DonutData
import com.graphsy.compose.models.DonutPathData
import com.graphsy.compose.models.DonutPathDataEntry
import com.graphsy.compose.models.SectionsPathData
import kotlin.math.min


@Composable
fun DonutChart(
    data: List<DonutData>,
    modifier: Modifier = Modifier,
    configuration: DonutChartConfiguration = DonutChartConfiguration()
) {
    val donutPathData = data.map {
        CreatePathData(
            it,
            configuration
        )
    }
    DrawDonut(modifier = modifier, data = donutPathData, configuration.direction)
    Canvas(modifier = modifier) {
        configuration.drawOverGraph?.invoke(this)
    }
}


@Composable
private fun DrawDonut(modifier: Modifier, data: List<DonutPathData>, direction: Direction) {
    Canvas(modifier = modifier) {
        data.forEachIndexed { index, item ->

            val size = Size(
                min(size.width / index.inc(), size.width - item.masterPathData.strokeWidth),
                min(size.height / index.inc(), size.height - item.masterPathData.strokeWidth),
            )
            val offset = Offset.Zero + Offset(
                x = (this.size.width - size.width).div(2f),
                y = (this.size.height - size.height).div(2f)
            )
            drawDonutSegment(
                item.masterPathData,
                direction,
                offset,
                size
            )
            item.entriesPathData.forEach { pathData ->
                drawDonutSegment(pathData, direction, offset, size)
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

@Composable
private fun CreatePathData(
    model: DonutData,
    config: DonutChartConfiguration
): DonutPathData {
    val masterProgress = model.masterSlice.circumferencePercentage
    val gapAngleDegrees = config.gapAngleDegrees.angle
    val gapWidthDegrees = config.gapWidthDegrees.angle
    val backgroundLineColor = model.masterSlice.color
    val halfGap = gapWidthDegrees / 2

    val wholeDonutAngle = 360f - gapWidthDegrees
    val masterSegmentAngle = wholeDonutAngle * masterProgress
    val startAngle = gapAngleDegrees + halfGap

    val masterPathData = DonutPathDataEntry(
        color = backgroundLineColor,
        startAngle = startAngle,
        sweepAngle = masterSegmentAngle,
        strokeWidth = model.masterSlice.strokeWidth
    )
    val sectionsPathData = DonutChartUtils.calculateSectionsPathData(
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


@Composable
fun FloatAnimation(target: Float): State<Float> {
    return animateFloatAsState(
        targetValue = target,
        animationSpec = simpleChartAnimation(),
        label = ""
    )
}

//private fun buildDonutFlow(
//    model: DonutData,
//    config: DonutChartConfiguration
//): DonutProgressValues {
//
//}
//
//private data class DonutProgressValues(
//
//)
