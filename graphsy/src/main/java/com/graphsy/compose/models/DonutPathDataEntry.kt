package com.graphsy.compose.models

import androidx.compose.ui.graphics.Color

internal data class DonutPathDataEntry(
    val color: Color,
    val strokeWidth: Float,
    val startAngle: Float,
    val sweepAngle: Float
)