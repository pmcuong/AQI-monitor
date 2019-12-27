package com.example.aqimonitor.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class SearchGlobalObject(
    @SerializedName("status")
    @Expose
    var status: String? = null,
    @SerializedName("data")
    @Expose
    var data: ArrayList<SearchLocationObject>? = null
)