package com.graphsy.compose.common

import com.graphsy.compose.config.DonutChartConfiguration
import com.graphsy.compose.models.DonutData
import com.graphsy.compose.models.DonutPathDataEntry
import com.graphsy.compose.models.SectionsPathData
import kotlin.math.max

object DonutChartUtils {
    internal fun calculatePathData(
        data: DonutData,
        configuration: DonutChartConfiguration
    ): List<DonutPathDataEntry> {
        val allEntriesAmount = data.sectionsSum
        val wholeDonutAmount = max(data.masterSlice.value, allEntriesAmount)
        val masterSegmentAmount = wholeDonutAmount * data.masterSlice.circumferencePercentage
        val wholeDonutAngle = 360f - configuration.gapWidthDegrees.angle
        val masterSegmentAngle = wholeDonutAngle * data.masterSlice.circumferencePercentage
        val halfGap = configuration.gapWidthDegrees.angle / 2
        val startAngle = configuration.gapAngleDegrees.angle + halfGap
        return createPathDataForSections(
            SectionsPathData(
                startAngle = startAngle,
                masterSegmentAmount = masterSegmentAmount,
                masterSegmentAngle = masterSegmentAngle,
                masterProgress = data.masterSlice.circumferencePercentage,
                sections = data.sections
            )
        )
    }

    internal inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
        var sum = 0f
        for (element in this) {
            sum += selector(element)
        }
        return sum
    }
    private fun createPathDataForSections(data: SectionsPathData): List<DonutPathDataEntry> = with(data) {
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
                startAngle = angleAccumulator,
                sweepAngle = entryAngle
            )

            angleAccumulator += entryAngle
        }

        return entriesPathData
    }
}