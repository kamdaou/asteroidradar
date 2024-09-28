package com.ampersand.data.service.dto

import com.google.gson.annotations.SerializedName

data class AsteroidDto(
    val id: Long,

    val codename: String,

    @SerializedName("close_approach_date")
    val closeApproachDate: String,

    @SerializedName("absolute_magnitude")
    val absoluteMagnitude: Double,

    @SerializedName("estimated_diameter")
    val estimatedDiameter: Double,

    @SerializedName("relative_velocity")
    val relativeVelocity: Double,

    @SerializedName("distance_from_earth")
    val distanceFromEarth: Double,

    @SerializedName("is_potentially_hazardous")
    val isPotentiallyHazardous: Boolean
)
