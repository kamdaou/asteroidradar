package com.ampersand.data.service.local.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ampersand.domain.model.AsteroidModel

@Entity(tableName = "asteroid_entity")
data class AsteroidEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val codename: String,

    @ColumnInfo("close_approach_date")
    val closeApproachDate: String,

    @ColumnInfo("absolute_magnitude")
    val absoluteMagnitude: Double,

    @ColumnInfo("estimated_diameter")
    val estimatedDiameter: Double,

    @ColumnInfo("relative_velocity")
    val relativeVelocity: Double,

    @ColumnInfo("distance_from_earth")
    val distanceFromEarth: Double,

    @ColumnInfo("is_potentially_hazardous")
    val isPotentiallyHazardous: Boolean
) {
    fun toAsteroidModel(): AsteroidModel {
        return AsteroidModel(
            id,
            codename,
            closeApproachDate,
            absoluteMagnitude,
            estimatedDiameter,
            relativeVelocity,
            distanceFromEarth,
            isPotentiallyHazardous
        )
    }
}
