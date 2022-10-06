package com.example.mapsactivity.remote.entity

import com.squareup.moshi.Json

data class LocationModel(
    @field:Json(name = "location")
    val location: LocationModel?
)