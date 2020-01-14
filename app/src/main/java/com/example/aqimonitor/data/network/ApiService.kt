package com.example.aqimonitor.data.network

import com.example.aqimonitor.data.network.model.air_quality.SearchResult
import com.example.aqimonitor.data.network.model.search.SearchGlobalObject
import com.example.aqimonitor.data.network.model.search.SearchLocationObject
import com.example.aqimonitor.utils.Constant
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Search City
    @GET("search/")
    fun fetchCityID(@Query("token") token: String = Constant.TOKEN, @Query("keyword") city: String): Observable<SearchGlobalObject>

    // Get the nearest station close to the user location
    @GET("feed/geo:{lat}/")
    fun fetchNearestStation(@Path("lat") latLng: String, @Query("token") token: String = Constant.TOKEN): Observable<SearchResult>
}