package com.graphsy.compose.state

import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.graphsy.compose.models.DonutData

internal data class DonutUiState(
    val gapAngle: State<Float>,
    val masterProgress: State<Float>,
    val gapWidthDegrees: State<Float>,
    val strokeWidth: State<Float>,
    val backgroundLineColor: State<Color>,
    val startAngles: List<State<Float>>,
    val sweepAngles: List<State<Float>>,
    val colors: List<State<Color>>,
    val pathData: DonutData
)