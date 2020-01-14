package com.example.aqimonitor.data.network.model.air_quality_position

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PositionStationObject(
    @SerializedName("aqi")
    @Expose
    var aqi: Int? = 0,
    @SerializedName("idx")
    @Expose
    var idx: Int? = 0,
    @SerializedName("dominentpol")
    @Expose
    var dominentpol: String? = "",
    @SerializedName("city")
    @Expose
    var city: PositionCityObject? = null
)