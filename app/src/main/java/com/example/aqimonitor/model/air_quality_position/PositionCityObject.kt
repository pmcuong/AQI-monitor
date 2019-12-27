package com.example.aqimonitor.model.air_quality_position

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class PositionCityObject(
    @SerializedName("geo")
    @Expose
    var geo: ArrayList<Float>? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("url")
    @Expose
    var url: String? = null
)