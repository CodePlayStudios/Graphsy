package com.graphsy.compose.models.internal

import com.graphsy.compose.models.internal.DonutPathDataEntry

internal data class DonutPathData(
    val masterPathData: DonutPathDataEntry,
    val entriesPathData: List<DonutPathDataEntry>
)
