package com.example.aqimonitor.model.search

import android.util.Log
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class SearchLocationObject(
    @SerializedName("uid")
    @Expose
    var uid: Int? = 0,
    @SerializedName("aqi")
    @Expose
    var aqi: String,
    @SerializedName("station")
    @Expose
    var station: SearchStationObject? = null
) {

    fun getAqiInFloat(): Float {
        try {
            return aqi.toFloat()
        } catch (e: Exception) {
            return 0f
        }
    }
}