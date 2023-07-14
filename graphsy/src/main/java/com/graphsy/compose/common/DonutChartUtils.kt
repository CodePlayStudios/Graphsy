package com.graphsy.compose.common

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.center
import com.graphsy.compose.config.DonutChartConfiguration
import com.graphsy.compose.models.DonutData
import com.graphsy.compose.models.internal.DonutPathDataEntry
import com.graphsy.compose.models.internal.SectionsPathData
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

internal object DonutChartUtils {
    fun calculateSectionsPathData(
        data: DonutData,
        configuration: DonutChartConfiguration
    ): List<DonutPathDataEntry> {
        val allEntriesAmount = data.sectionsSum
        val wholeDonutAmount = max(data.masterSlice.value, allEntriesAmount)
        val masterSegmentAmount = wholeDonutAmount * data.masterSlice.circumferencePercentage
        val halfGap = configuration.gapWidthDegrees.angle / 2
        val wholeDonutAngle = 360f - configuration.gapWidthDegrees.angle
        val masterSegmentAngle = wholeDonutAngle * data.masterSlice.circumferencePercentage
        val startAngle = configuration.gapAngleDegrees.angle + halfGap
        return createPathDataForSections(
            SectionsPathData(
                startAngle = startAngle,
                masterSegmentAmount = masterSegmentAmount,
                masterSegmentAngle = masterSegmentAngle,
                masterProgress = data.masterSlice.circumferencePercentage,
                masterStrokeWidth = data.masterSlice.strokeWidth,
                sections = data.sections
            )
        )
    }

    inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
        var sum = 0f
        for (element in this) {
            sum += selector(element)
        }
        return sum
    }

    private fun createPathDataForSections(data: SectionsPathData): List<DonutPathDataEntry> =
        with(data) {
            var angleAccumulator = startAngle
            val entriesPathData = mutableListOf<DonutPathDataEntry>()

            for (entry in sections) {
                val entryAngle = if (masterSegmentAmount != 0f) {
                    masterSegmentAngle * (entry.value / masterSegmentAmount) * masterProgress
                } else {
                    0f
                }

                entriesPathData += DonutPathDataEntry(
                    color = entry.color,
                    strokeWidth = data.masterStrokeWidth,
                    startAngle = angleAccumulator,
                    sweepAngle = entryAngle
                )

                angleAccumulator += entryAngle
            }

            return entriesPathData
        }

    internal val Float.degreeToAngle
        get() = (this * Math.PI / 180f).toFloat()

    internal fun DrawScope.calculateLabelPosition(
        textMeasureResult: TextLayoutResult,
        direction: Path.Direction,
        pathData: DonutPathDataEntry
    ): Offset {
        val textCenter = textMeasureResult.size.center
        val factor = if (direction == Path.Direction.CCW) -1 else 1
        val angleInRadians =
            ((pathData.startAngle + pathData.sweepAngle / 2).degreeToAngle) * factor
        val radius = size.width / 2f
        val innerRadius = radius - pathData.strokeWidth

        return Offset(
            -textCenter.x + center.x + (innerRadius + pathData.strokeWidth) * cos(
                angleInRadians
            ),
            -textCenter.y + center.y + (innerRadius + pathData.strokeWidth) * sin(
                angleInRadians
            )
        )
    }
}

