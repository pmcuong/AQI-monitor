package com.example.aqimonitor.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class SearchLocationObject(
    @SerializedName("uid")
    @Expose
    var uid: Int? = 0,
    @SerializedName("station")
    @Expose
    var station: SearchStationObject? = null
)