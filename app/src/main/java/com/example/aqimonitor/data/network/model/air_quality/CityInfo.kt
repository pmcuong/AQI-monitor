package com.example.aqimonitor.data.network.model.air_quality

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class CityInfo(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("geo")
    @Expose
    var geo: ArrayList<Float>

) {
    fun getFullAddress(): String {
        var fullAddress: String
        if (name.contains("(") && name.contains(")")) {
            fullAddress = name?.substring(name.lastIndexOf("(") + 1, name.lastIndexOf(")"))
        } else {
            fullAddress = name
        }
        return fullAddress.trim()
    }

    fun getNameAddress(): String {
        var nameAdress: String
        var fullAddress = getFullAddress()
        var arrString: List<String> = fullAddress.split(",")
        nameAdress = arrString[0]
        return nameAdress.trim()
    }
}