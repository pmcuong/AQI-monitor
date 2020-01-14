package com.example.aqimonitor.data.network.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class MessageObject(
    @SerializedName("timestamp")
    @Expose
    var timestamp: Long? = null,
    @SerializedName("city")
    @Expose
    var city: CityObject? = null,
    @SerializedName("forecast")
    @Expose
    var forecast: ForecastObject? = null,
    @SerializedName("iaqi")
    @Expose
    var iaqi: ArrayList<IaqiObject>? = null,
    @SerializedName("aqi")
    @Expose
    var aqi: Int? = null
)