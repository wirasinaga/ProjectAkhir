package com.example.mapsactivity.json

import com.example.mapsactivity.data.Tempat
import com.google.gson.annotations.SerializedName

data class ResponseAcoSorting(
    @SerializedName("message") val message: String = "",
    @SerializedName("sorted") val sorted: List<Tempat> = listOf()
) {
    data class RequestBody(
        @SerializedName("locations") val locations: List<Tempat> = listOf(),
        @SerializedName("start_location") val startLocation: Tempat = Tempat(),
        @SerializedName("end_location") val endLocation: Tempat = Tempat()
    )
}
