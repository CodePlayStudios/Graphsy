package com.graphsy.compose.models

import com.graphsy.compose.common.DonutChartUtils.sumByFloat

data class DonutData(
    val masterSlice: DonutSlice.MasterSlice,
    val sections: List<DonutSlice.SectionSlice>,
) {
    /**
     * Sum of all [DonutSlice.value]
     */
    val sectionsSum: Float get() = sections.sumByFloat { it.value }
}

