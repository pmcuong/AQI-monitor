package com.example.aqimonitor.data

import com.example.aqimonitor.data.database.DBHelper
import com.example.aqimonitor.data.network.ApiHelper
import com.example.aqimonitor.data.network.ApiService
import com.example.aqimonitor.model.AQIModel
import io.reactivex.Observable

interface DataManager : DBHelper, ApiHelper {
    fun fetchListAqiFromCity(city: String): Observable<List<AQIModel>>

    fun getAqiModelNearest(aqiModel: AQIModel): Observable<AQIModel>
}