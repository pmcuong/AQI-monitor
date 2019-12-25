package com.example.aqimonitor.model

class AQIModel {
    var nameAddress: String?
    var address: String?
    var aqiIndex: Int?
    constructor(nameAddress: String, address: String? = null, aqiIndex: Int = 0  ){
        this.nameAddress = nameAddress
        this.address = address
        this.aqiIndex = aqiIndex
    }
}

