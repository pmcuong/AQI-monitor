package com.example.aqimonitor.data.network.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class ForecastObject(
    @SerializedName("aqi")
    @Expose
    var aqi: ArrayList<AqiObject>? = null
)