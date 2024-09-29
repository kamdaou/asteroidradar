package com.ampersand.presentation

import android.os.Parcelable
import com.ampersand.core.UiText
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(
    val id: Long = 0L,

    val codename: String = "",

    val closeApproachDate: UiText? = null,

    val absoluteMagnitude: Double = 0.0,

    val estimatedDiameter: Double = 0.0,

    val relativeVelocity: Double = 0.0,

    val distanceFromEarth: Double = 0.0,

    val isPotentiallyHazardous: Boolean = false
) : Parcelable
