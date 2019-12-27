package com.example.aqimonitor.network

interface ResultCallback {
    fun onError(errCode: String)
    fun onSuccess(response: Any)
}