package com.graphsy.compose.models

internal data class SectionsPathData(
    val startAngle: Float,
    val masterSegmentAmount: Float,
    val masterSegmentAngle: Float,
    val masterProgress: Float,
    val sections: List<DonutSlice.SectionSlice>
)
