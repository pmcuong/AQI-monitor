package com.example.aqimonitor.data.network.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class SearchStationObject(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("geo")
    @Expose
    val geo: ArrayList<Double>
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