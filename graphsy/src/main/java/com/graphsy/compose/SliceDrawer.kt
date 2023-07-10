package com.graphsy.compose

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.graphsy.compose.config.DonutChartConfiguration

interface SliceDrawer {
    fun drawSlice(
        drawScope: DrawScope,
        canvas: Canvas,
        area: Size,
        donutChartConfiguration: DonutChartConfiguration
    )
}