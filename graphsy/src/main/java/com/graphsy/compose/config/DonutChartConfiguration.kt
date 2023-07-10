package com.graphsy.compose.config

import androidx.compose.ui.graphics.Color
import com.graphsy.compose.common.StartAngle


/**
 * Configuration for the axes and grid lines in a chart.
 *
 * @param innerDonutSpacing Used for setting space with nested donuts
 * @param strokeWidth The stroke width of the donut sections
 * @param gapWidthDegrees Width degree is amount gap required in master donut
 * @param gapAngleDegrees Degree used as a gap start point in donut
 * @param isLabelsEnabled Whether to show the label for each section
 * @param isAnimationEnabled Whether to show initial draw animation
 * @param backgroundLineColor donut background LineColor
 */
data class DonutChartConfiguration(
    val innerDonutSpacing: Float = 1f,
    val strokeWidth: Float = 100f,
    val gapWidthDegrees: StartAngle = StartAngle.CustomAngle(angle = 0.2f),
    val gapAngleDegrees: StartAngle = StartAngle.ReflexAngle,
    val isLabelsEnabled: Boolean = true,
    val isAnimationEnabled: Boolean = true,
    val backgroundLineColor: Color = Color.Gray
)