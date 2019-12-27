package com.example.aqimonitor.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class ForecastObject(
    @SerializedName("aqi")
    @Expose
    var aqi: ArrayList<AqiObject>? = null
)