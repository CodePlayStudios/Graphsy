package com.graphsy.compose.config

import android.graphics.Path.Direction
import com.graphsy.compose.common.StartAngle


/**
 * Configuration for the axes and grid lines in a chart.
 *
 * @param strokeWidth The stroke width of the donut sections
 * @param gapWidthDegrees Width degree is amount gap required in master donut
 * @param gapAngleDegrees Degree used as a gap start point in donut
 * @param isLabelsEnabled Whether to show the label for each section
 * @param isAnimationEnabled Whether to show initial draw animation
 */
data class DonutChartConfiguration(
    val strokeWidth: Float = 100f,
    val gapWidthDegrees: StartAngle = StartAngle.CustomAngle(angle = 0f),
    val gapAngleDegrees: StartAngle = StartAngle.ReflexAngle,
    val isLabelsEnabled: Boolean = true,
    val isAnimationEnabled: Boolean = true,
    val direction: Direction = Direction.CW
)