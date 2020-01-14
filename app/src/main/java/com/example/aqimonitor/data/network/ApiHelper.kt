package com.example.aqimonitor.data.network

import com.example.aqimonitor.data.network.model.air_quality.SearchResult
import com.example.aqimonitor.data.network.model.search.SearchGlobalObject
import com.example.aqimonitor.data.network.model.search.SearchLocationObject
import com.example.aqimonitor.model.AQIModel
import io.reactivex.Observable
import io.reactivex.Single

interface ApiHelper {
    fun fetchCityID(city: String): Observable<SearchGlobalObject>

    fun fetchNearestStation(latLng: String): Observable<SearchResult>

    fun fetchAllAqiData(listAQIModel: List<AQIModel>): Observable<List<AQIModel>>
}