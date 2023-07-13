package com.graphsy.compose.donut

import android.graphics.Path.Direction
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.graphsy.compose.animation.simpleChartAnimation
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
    val transitionProgress = remember(data) { Animatable(initialValue = 0f) }
    LaunchedEffect(data) {
        transitionProgress.animateTo(1f, animationSpec = simpleChartAnimation())
    }

    val donutPathData = data.map {
        val sections = DonutChartUtils.calculateSectionsPathData(
            data = it,
            configuration = configuration
        )
        createDonutPathData(
            donutUiState = buildDonutUiState(
                model = it,
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
        drawOverGraph = configuration.drawOverGraph
    )
}


@Composable
private fun DrawDonut(
    modifier: Modifier,
    data: List<DonutPathData>,
    direction: Direction,
    drawOverGraph: (DrawScope.() -> Unit)? = null
) {
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


private fun createDonutPathData(
    donutUiState: DonutUiState,
    sectionsSize: Int
): DonutPathData {
    val masterProgress = donutUiState.animatedMasterProgress.value
    val gapAngleDegrees = donutUiState.animatedGapAngle.value
    val gapWidthDegrees = donutUiState.animatedGapWidthDegrees.value
    val backgroundLineColor = donutUiState.animatedBackgroundLineColor.value
    val strokeWidth = donutUiState.animatedStrokeWidth.value

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
            color = donutUiState.animatedColors[index].value,
            startAngle = donutUiState.animatedStartAngles[index].value,
            sweepAngle = donutUiState.animatedSweepAngles[index].value,
            strokeWidth = strokeWidth
        )
    }
    return DonutPathData(masterPathData, entriesPathData)
}


@Composable
fun floatAnimationState(target: Float): State<Float> {
    return animateFloatAsState(
        targetValue = target,
        animationSpec = tween(
            durationMillis = 1000,
            easing = CubicBezierEasing(0.18f, 0.7f, 0.16f, 1f)
        ),
        label = ""
    )
}

@Composable
private fun buildDonutUiState(
    model: DonutData,
    config: DonutChartConfiguration,
    sectionsPathData: List<DonutPathDataEntry>
): DonutUiState {
    val animatedBackgroundLineColor = animateColorAsState(
        targetValue = model.masterSlice.color,
        animationSpec = tween(),
        label = ""
    )
    val animatedProgressColors = model.sections.map {
        animateColorAsState(
            targetValue = it.color,
            animationSpec = tween(),
            label = ""
        )
    }
    return DonutUiState(
        animatedGapAngle = floatAnimationState(target = config.gapAngleDegrees.angle),
        animatedMasterProgress = floatAnimationState(target = model.masterSlice.circumferencePercentage),
        animatedGapWidthDegrees = floatAnimationState(target = config.gapWidthDegrees.angle),
        animatedStrokeWidth = floatAnimationState(target = model.masterSlice.strokeWidth),
        animatedBackgroundLineColor = animatedBackgroundLineColor,
        animatedStartAngles = model.sections.mapIndexed { index, _ ->
            floatAnimationState(target = sectionsPathData[index].startAngle)
        },
        animatedSweepAngles = model.sections.mapIndexed { index, _ ->
            floatAnimationState(target = sectionsPathData[index].sweepAngle)
        },
        animatedColors = animatedProgressColors
    )
}

private data class DonutUiState(
    val animatedGapAngle: State<Float>,
    val animatedMasterProgress: State<Float>,
    val animatedGapWidthDegrees: State<Float>,
    val animatedStrokeWidth: State<Float>,
    val animatedBackgroundLineColor: State<Color>,
    val animatedStartAngles: List<State<Float>>,
    val animatedSweepAngles: List<State<Float>>,
    val animatedColors: List<State<Color>>
)
