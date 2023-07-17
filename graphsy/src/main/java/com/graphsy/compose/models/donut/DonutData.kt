package com.graphsy.compose.models.donut

import com.graphsy.compose.common.DonutChartUtils.sumByFloat

data class DonutData(
    val masterSlice: DonutSlice.MasterSlice,
    val sections: List<DonutSlice.SectionSlice>
) {
    /**
     * Sum of all [DonutSlice.value]
     */
    internal val sectionsSum: Float get() = sections.sumByFloat { it.value }
}

