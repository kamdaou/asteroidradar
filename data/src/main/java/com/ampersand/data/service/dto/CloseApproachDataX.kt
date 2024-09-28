package com.ampersand.data.service.dto

data class CloseApproachDataX(
    val close_approach_date: String,
    val close_approach_date_full: String,
    val epoch_date_close_approach: Long,
    val miss_distance: MissDistanceX,
    val orbiting_body: String,
    val relative_velocity: RelativeVelocityX
)