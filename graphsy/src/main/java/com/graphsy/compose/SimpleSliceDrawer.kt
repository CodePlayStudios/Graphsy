//package com.graphsy.compose
//
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Rect
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Canvas
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Paint
//import androidx.compose.ui.graphics.PaintingStyle
//import androidx.compose.ui.graphics.StrokeCap
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.graphics.drawscope.Stroke
//import com.graphsy.compose.models.DonutSlice
//
//class SimpleSliceDrawer(private val sliceThickness: Float = 25f) : SliceDrawer {
//    init {
//        require(sliceThickness in 10f..100f) {
//            "Thickness of $sliceThickness must be between 10-100"
//        }
//    }
//
//    private val sectionPaint = Paint().apply {
//        isAntiAlias = true
//        style = PaintingStyle.Stroke
//    }
//
//    override fun drawSlice(
//        drawScope: DrawScope,
//        canvas: Canvas,
//        area: Size,
//        startAngle: Float,
//        sweepAngle: Float,
//        slice: DonutSlice
//    ) {
//        val sliceThickness = calculateSectorThickness(area = area)
//        val drawableArea = calculateDrawableArea(area = area)
//
//        val draw: (
//            color: Color,
//            sweepAngle: Float,
//            arcStartAngle: Float
//        ) -> Unit = { arcColor, arcAngle, arcStartAngle ->
//            canvas.drawArc(
//                rect = drawableArea,
//                paint = sectionPaint.apply {
//                    color = arcColor
//                    strokeWidth = sliceThickness
//                    strokeCap = StrokeCap.Butt
//                },
//                startAngle = arcStartAngle,
//                sweepAngle = arcAngle,
//                useCenter = false
//            )
//        }
//        if (slice is CircleProgress) {
//            draw.invoke(slice.color, sweepAngle, startAngle)
//            val arc = PieChartUtils.caluclateInnerSlice(
//                parentSweep = sweepAngle,
//                progress = slice.innerSlice
//            )
//            draw.invoke(slice.color, sweepAngle, startAngle)
//        }
//    }
//
//    private fun calculateSectorThickness(area: Size): Float {
//        val minSize = minOf(area.width, area.height)
//
//        return minSize * (sliceThickness / 200f)
//    }
//
//    private fun DrawScope.drawDonutSegment(
//        strokeWidth: Float,
//        strokeCap: StrokeCap,
//        data: DonutPathDataEntry
//    ) {
//        drawArc(
//            color = data.color,
//            startAngle = data.startAngle,
//            sweepAngle = data.sweepAngle,
//            useCenter = false,
//            topLeft = Offset.Zero + Offset(
//                x = strokeWidth / 2f,
//                y = strokeWidth / 2f
//            ),
//            size = Size(
//                size.width - strokeWidth,
//                size.height - strokeWidth
//            ),
//            style = Stroke(
//                width = strokeWidth,
//                cap = strokeCap
//            )
//        )
//    }
//    private fun calculateDrawableArea(area: Size): Rect {
//        val sliceThicknessOffset =
//            calculateSectorThickness(area = area) / 2f
//        val offsetHorizontally = (area.width - area.height) / 2f
//
//        return Rect(
//            left = sliceThicknessOffset + offsetHorizontally,
//            top = sliceThicknessOffset,
//            right = area.width - sliceThicknessOffset - offsetHorizontally,
//            bottom = area.height - sliceThicknessOffset
//        )
//    }
//}