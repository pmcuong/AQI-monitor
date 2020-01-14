package com.example.aqimonitor.data.network.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class AqiObject(
    @SerializedName("t")
    @Expose
    var t: String? = null,
    @SerializedName("v")
    @Expose
    var v: ArrayList<Int>? = null
)