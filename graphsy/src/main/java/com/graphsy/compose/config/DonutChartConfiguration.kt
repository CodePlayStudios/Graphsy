package com.graphsy.compose.config

import android.graphics.Path.Direction
import androidx.compose.animation.core.AnimationSpec
import com.graphsy.compose.animation.simpleChartAnimation
import com.graphsy.compose.models.StartAngle


/**
 * Configuration for the axes and grid lines in a chart.
 *
 * @param strokeWidth The stroke width of the donut sections
 * @param gapWidthDegrees Width degree is amount gap required in master donut
 * @param gapAngleDegrees Degree used as a gap start point in donut
 * @param isLabelsEnabled Whether to show the label for each section
 * @param animation customised, animation setup
 */
data class DonutChartConfiguration(
    val strokeWidth: Float = 100f,
    val gapWidthDegrees: StartAngle = StartAngle.CustomAngle(angle = 0f),
    val gapAngleDegrees: StartAngle = StartAngle.ReflexAngle,
    val isLabelsEnabled: Boolean = true,
    val animation: AnimationSpec<Float> = simpleChartAnimation(),
    val direction: Direction = Direction.CW
)