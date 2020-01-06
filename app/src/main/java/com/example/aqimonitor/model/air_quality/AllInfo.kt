package com.example.aqimonitor.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllInfo(
    @SerializedName("idx")
    @Expose
    val idx: Int,
    @SerializedName("aqi")
    @Expose
    val aqi: Float,
    @SerializedName("time")
    @Expose
    val timeInfo: TimeInfo,
    @SerializedName("city")
    @Expose
    val cityInfo: CityInfo
)