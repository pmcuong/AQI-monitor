package com.example.aqimonitor.model

class AQIModel {
    var nameAddress: String? = null
    var address: String? = null
    var aqiIndex: Int? = 0;
    constructor(nameAddress: String, address: String? = null, aqiIndex: Int? = 0 ){
        this.nameAddress = nameAddress
        this.address = address
        this.aqiIndex = aqiIndex
        address?.toInt()
    }
}

