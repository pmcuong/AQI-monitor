package com.example.aqimonitor.network

import com.example.aqimonitor.model.air_quality.SearchResult
import com.example.aqimonitor.model.search.SearchGlobalObject
import com.example.aqimonitor.utils.Constant
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Search City
    @GET("search/")
    fun fetchCityID(@Query("token") token: String = Constant.TOKEN, @Query("keyword") city: String): Single<SearchGlobalObject>

    // Get the nearest station close to the user location
    @GET("feed/geo:{lat}/")
    fun fetchNearestStation(@Path("lat") latLng: String, @Query("token") token: String = Constant.TOKEN): Single<SearchResult>
}