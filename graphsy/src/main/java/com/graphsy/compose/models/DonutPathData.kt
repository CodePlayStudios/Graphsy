package com.graphsy.compose.models

internal data class DonutPathData(
    val masterPathData: DonutPathDataEntry,
    val entriesPathData: List<DonutPathDataEntry>
)
