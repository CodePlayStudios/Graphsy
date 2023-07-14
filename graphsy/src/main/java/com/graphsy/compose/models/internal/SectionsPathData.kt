package com.graphsy.compose.models.internal

import com.graphsy.compose.models.DonutSlice

internal data class SectionsPathData(
    val startAngle: Float,
    val masterSegmentAmount: Float,
    val masterSegmentAngle: Float,
    val masterProgress: Float,
    val masterStrokeWidth: Float,
    val sections: List<DonutSlice.SectionSlice>
)
