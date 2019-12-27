package com.example.aqimonitor.model.air_quality_position

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class PositionGlobalObject(
    @SerializedName("status")
    @Expose
    var status: String? = null,
    @SerializedName("data")
    @Expose
    var data: PositionStationObject? = null
) {

    fun getName(): String? {
        return data?.city?.name
    }

    fun getGPSCoordinate(): String? {
        return String.format("GPS: %.6f - %.6f", data?.city?.geo?.get(0), data?.city?.geo?.get(1))
    }

    fun getAqi(): String? {
        return data?.aqi.toString()
    }
}