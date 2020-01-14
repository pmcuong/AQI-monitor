package com.example.aqimonitor.data.network.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class CityObject(
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("url")
    @Expose
    var url: String? = null,
    @SerializedName("url")
    @Expose
    var idx: Int? = null,
    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("geo")
    @Expose
    var geo: ArrayList<Float>? = null
)