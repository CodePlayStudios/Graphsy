package com.graphsy.compose.models.internal

import androidx.compose.ui.graphics.Color

internal data class DonutPathDataEntry(
    val color: Color,
    val labelText: String? = null,
    val strokeWidth: Float,
    val startAngle: Float,
    val sweepAngle: Float
)