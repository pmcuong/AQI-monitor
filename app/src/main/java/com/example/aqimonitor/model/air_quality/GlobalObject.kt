package com.example.aqimonitor.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class GlobalObject(
    @SerializedName("dt")
    @Expose
    var dt: String? = null,
    @SerializedName("rxs")
    @Expose
    var rxs: RxsObject? = null
)