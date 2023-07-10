package com.graphsy.compose.common

import androidx.compose.runtime.Immutable

@Immutable
sealed class StartAngle(open val angle: Float) {
    /**
     * Start angle of 0 degrees.
     */
    object Zero : StartAngle(0F)

    /**
     * Start angle of 180 degrees (straight angle).
     */
    object StraightAngle : StartAngle(180F)

    /**
     * Start angle of 270 degrees (reflex angle).
     */
    object ReflexAngle : StartAngle(270F)

    /**
     * Start angle of 90 degrees (right angle).
     */
    object RightAngle : StartAngle(90F)

    /**
     * Custom start angle specified by the given angle in degrees.
     *
     * @param angle The custom start angle in degrees.
     */
    data class CustomAngle(override val angle: Float) : StartAngle(angle) {
        init {
            require(angle in 0.0f..1.0f) { "angle must be within the range of 0f to 360f" }
        }
    }
}
