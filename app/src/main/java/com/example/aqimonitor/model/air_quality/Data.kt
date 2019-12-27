package com.example.aqimonitor.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class Data(
    @SerializedName("msg")
    @Expose
    var msg: MessageObject? = null,
    @SerializedName("status")
    @Expose
    var status: String? = null
)