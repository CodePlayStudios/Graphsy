@file:OptIn(ExperimentalTextApi::class)

package com.graphsy.compose.donut

import android.graphics.Path.Direction
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.sp
import com.graphsy.compose.common.DonutChartUtils
import com.graphsy.compose.common.DonutChartUtils.degreeToAngle
import com.graphsy.compose.config.DonutChartConfiguration
import com.graphsy.compose.models.DonutData
import com.graphsy.compose.models.internal.DonutPathData
import com.graphsy.compose.models.internal.DonutPathDataEntry
import com.graphsy.compose.state.DonutUiState
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


@Composable
fun DonutChart(
    data: List<DonutData>,
    modifier: Modifier = Modifier,
    configuration: DonutChartConfiguration = DonutChartConfiguration()
) {
    val donutPathData = data.map {
        val sections = DonutChartUtils.calculateSectionsPathData(
            data = it,
            configuration = configuration
        )
        createDonutPathData(
            donutUiState = buildDonutUiState(
                donutData = it,
                config = configuration,
                sectionsPathData = sections
            ),
            sectionsSize = sections.size
        )
    }
    DrawDonut(
        modifier = modifier,
        data = donutPathData,
        direction = configuration.direction,
        isLabelEnabled = configuration.isLabelsEnabled,
        drawOverGraph = configuration.drawOverGraph
    )
}


@OptIn(ExperimentalTextApi::class)
@Composable
private fun DrawDonut(
    modifier: Modifier,
    data: List<DonutPathData>,
    direction: Direction,
    drawOverGraph: (DrawScope.() -> Unit)? = null,
    isLabelEnabled: Boolean
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier = modifier) {
        drawOverGraph?.invoke(this)
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
                item.entriesPathData[index].labelText?.run {
                    if (isLabelEnabled) {
                        drawLabel(pathData, size, direction, textMeasurer, this)
                    }
                }
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

private fun DrawScope.drawLabel(
    pathData: DonutPathDataEntry,
    size: Size,
    direction: Direction,
    textMeasurer: TextMeasurer,
    labelText: String
) {
    val radius = size.width / 2f
    val innerRadius = radius - pathData.strokeWidth
    val textMeasureResult = textMeasurer.measure(
        text = labelText,
        style = TextStyle(
            fontSize = 10.sp,
            color = Color.White,
            background = pathData.color
        )
    )
    val textCenter = textMeasureResult.size.center
    val factor = if (direction == Direction.CCW) -1 else 1
    val angleInRadians = ((pathData.startAngle + pathData.sweepAngle / 2).degreeToAngle) * factor

    val offset = Offset(
        -textCenter.x + center.x + (innerRadius + pathData.strokeWidth) * cos(
            angleInRadians
        ),
        -textCenter.y + center.y + (innerRadius + pathData.strokeWidth) * sin(
            angleInRadians
        )
    )

    drawText(
        textLayoutResult = textMeasureResult,
        topLeft = offset
    )
}

private fun createDonutPathData(
    donutUiState: DonutUiState,
    sectionsSize: Int
): DonutPathData {
    val masterProgress = donutUiState.masterProgress.value
    val gapAngleDegrees = donutUiState.gapAngle.value
    val gapWidthDegrees = donutUiState.gapWidthDegrees.value
    val backgroundLineColor = donutUiState.backgroundLineColor.value
    val strokeWidth = donutUiState.strokeWidth.value

    val wholeDonutAngle = 360f - gapWidthDegrees
    val masterSegmentAngle = wholeDonutAngle * masterProgress
    val startAngle = gapAngleDegrees + gapWidthDegrees / 2

    val masterPathData = DonutPathDataEntry(
        color = backgroundLineColor,
        startAngle = startAngle,
        sweepAngle = masterSegmentAngle,
        strokeWidth = strokeWidth
    )
    val entriesPathData = List(sectionsSize) { index ->
        DonutPathDataEntry(
            color = donutUiState.colors[index].value,
            startAngle = donutUiState.startAngles[index].value,
            sweepAngle = donutUiState.sweepAngles[index].value,
            strokeWidth = strokeWidth,
            labelText = "%.2f".format(donutUiState.pathData.sections[index].value * 100)
        )
    }
    return DonutPathData(masterPathData, entriesPathData)
}

@Composable
private fun floatAnimationState(target: Float): State<Float> {
    val spec: AnimationSpec<Float> = tween(
        durationMillis = 1000,
        easing = CubicBezierEasing(0.18f, 0.7f, 0.16f, 1f)
    )
    return animateFloatAsState(
        targetValue = target,
        animationSpec = spec,
        label = ""
    )
}

@Composable
private fun buildDonutUiState(
    donutData: DonutData,
    config: DonutChartConfiguration,
    sectionsPathData: List<DonutPathDataEntry>
): DonutUiState {
    val animatedBackgroundLineColor = animateColorAsState(
        targetValue = donutData.masterSlice.color,
        animationSpec = tween(),
        label = ""
    )
    val animatedProgressColors = donutData.sections.map {
        animateColorAsState(
            targetValue = it.color,
            animationSpec = tween(),
            label = ""
        )
    }

    return DonutUiState(
        gapAngle = floatAnimationState(target = config.gapAngleDegrees.angle),
        masterProgress = floatAnimationState(target = donutData.masterSlice.circumferencePercentage),
        gapWidthDegrees = floatAnimationState(target = config.gapWidthDegrees.angle),
        strokeWidth = floatAnimationState(target = donutData.masterSlice.strokeWidth),
        backgroundLineColor = animatedBackgroundLineColor,
        startAngles = List(donutData.sections.size) { index ->
            floatAnimationState(target = sectionsPathData[index].startAngle)
        },
        sweepAngles = List(donutData.sections.size) { index ->
            floatAnimationState(target = sectionsPathData[index].sweepAngle)
        },
        colors = animatedProgressColors,
        pathData = donutData
    )
}