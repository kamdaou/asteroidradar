package com.ampersand.domain.model

data class AsteroidModel(
    val id: Long = 0L,

    val codename: String,

    val closeApproachDate: String,

    val absoluteMagnitude: Double,

    val estimatedDiameter: Double,

    val relativeVelocity: Double,

    val distanceFromEarth: Double,

    val isPotentiallyHazardous: Boolean
)
