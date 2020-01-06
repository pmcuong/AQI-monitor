package com.example.aqimonitor.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("status")
    @Expose
    var status: String,
    @SerializedName("data")
    @Expose
    var allInfo: AllInfo
)