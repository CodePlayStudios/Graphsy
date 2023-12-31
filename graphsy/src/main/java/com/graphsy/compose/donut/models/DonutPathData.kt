package com.graphsy.compose.donut.models

internal data class DonutPathData(
    val masterPathData: DonutPathDataEntry,
    val entriesPathData: List<DonutPathDataEntry>
)
