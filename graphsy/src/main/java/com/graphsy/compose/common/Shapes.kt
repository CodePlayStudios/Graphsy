package com.graphsy.compose.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.verticalDottedLine(
    color: Color = Color.Gray,
    strokeWidth: Float = 2f,
    extraLineHeight: Float = 45f
) {
    val pathEffect =
        PathEffect.dashPathEffect(floatArrayOf(3f, 5f), 0f)

    drawLine(
        strokeWidth = strokeWidth,
        color = color,
        start = Offset(size.width.div(2), -extraLineHeight),
        end = Offset(size.width.div(2), size.height + extraLineHeight),
        pathEffect = pathEffect
    )
}