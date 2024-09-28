package com.ampersand.data.service.dto

import com.google.gson.annotations.SerializedName

data class AsteroidsResponse(
    @SerializedName("element_count")
    val elementCount: Int,
    val links: Links,
    @SerializedName("near_earth_objects")
    val nearEarthObjects: NearEarthObjects
)
