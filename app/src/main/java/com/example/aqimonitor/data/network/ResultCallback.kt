package com.example.aqimonitor.data.network

interface ResultCallback {
    fun onError(errCode: String)
    fun onSuccess(response: Any)
}