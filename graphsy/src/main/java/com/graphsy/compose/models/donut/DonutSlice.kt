package com.graphsy.compose.models.donut

import androidx.compose.ui.graphics.Color

sealed interface DonutSlice {
    val value: Float
    val color: Color

    data class MasterSlice(
        val circumferencePercentage: Float,
        val strokeWidth: Float = 100f,
        override val value: Float,
        override val color: Color,
    ) : DonutSlice {
        init {
            require(circumferencePercentage in 0.0f..1.0f) { "circumferencePercentage must be within the range of 0f to 1f" }
            require(strokeWidth in 1f..200.0f) { "strokeWidth must be within the range of 1f to 200f" }
        }
    }

    data class SectionSlice(
        override val value: Float,
        override val color: Color
    ) : DonutSlice
}