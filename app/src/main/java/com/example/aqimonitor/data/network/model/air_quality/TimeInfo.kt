package com.example.aqimonitor.data.network.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TimeInfo(
    @SerializedName("v")
    @Expose
    val timeInMilisecond: Long
)