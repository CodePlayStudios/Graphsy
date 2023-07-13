package com.graphsy.compose.config

import android.graphics.Path.Direction
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.graphsy.compose.models.StartAngle


/**
 * Configuration for the axes and grid lines in a chart.
 *
 * @param gapWidthDegrees Width degree is amount gap required in master donut
 * @param gapAngleDegrees Degree used as a gap start point in donut
 * @param isLabelsEnabled Whether to show the label for each section
 */
data class DonutChartConfiguration(
    val gapWidthDegrees: StartAngle = StartAngle.CustomAngle(angle = 0f),
    val gapAngleDegrees: StartAngle = StartAngle.ReflexAngle,
    val isLabelsEnabled: Boolean = true,
    val direction: Direction = Direction.CW,
    val drawOverGraph: (DrawScope.() -> Unit)? = null
)