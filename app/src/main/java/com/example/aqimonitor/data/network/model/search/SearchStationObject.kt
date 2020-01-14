package com.example.aqimonitor.data.network.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class SearchStationObject(
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("geo")
    @Expose
    var geo: ArrayList<Double>? = null
)