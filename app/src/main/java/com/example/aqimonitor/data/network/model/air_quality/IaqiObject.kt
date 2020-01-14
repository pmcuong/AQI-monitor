package com.example.aqimonitor.data.network.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class IaqiObject(
    @SerializedName("p")
    @Expose
    var p: String? = null,
    @SerializedName("v")
    @Expose
    var v: ArrayList<Int>? = null,
    @SerializedName("i")
    @Expose
    var i: String? = null
)