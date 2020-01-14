package com.example.aqimonitor.data.network

import android.util.Log
import com.example.aqimonitor.data.database.AppDBHelper
import com.example.aqimonitor.data.network.model.air_quality.SearchResult
import com.example.aqimonitor.data.network.model.search.SearchGlobalObject
import com.example.aqimonitor.data.network.model.search.SearchLocationObject
import com.example.aqimonitor.extention.getLatLngInString
import com.example.aqimonitor.model.AQIModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class AppApiHelper : ApiHelper {
    private var apiService: ApiService = ApiClient.client.create(ApiService::class.java)

    private object HOLDER {
        val INSTANCE = AppApiHelper()
    }

    companion object {
        val instance: AppApiHelper by lazy {
            HOLDER.INSTANCE
        }
    }
    override fun fetchCityID(city: String): Observable<SearchGlobalObject> {
        return apiService.fetchCityID(city = city.trim())

    }

    override fun fetchNearestStation(latLng: String): Observable<SearchResult> {
        return apiService.fetchNearestStation(latLng)
    }

//    fun fetchCityByName(name: String, callback: ResultCallback, repository: AppDBHelper) {
//        disposable.add(
//            apiService
//                .fetchCityID(city = name.trim())
//                .map { response ->
//                    val list: ArrayList<AQIModel> = ArrayList()
//                    for (item in response.data!!) {
//                        val lat = item.station?.geo?.get(0)
//                        val long = item.station?.geo?.get(1)
//                        val nameAddress = item.station?.name
//                        val address = item.station?.name
//                        val aqiIndex = item.getAqiInFloat()
//                        val uid = item.uid
//                        Log.d(
//                            "AppApiHelper",
//                            "onSuccess: " + uid + ", " + repository.getItemById(uid!!)
//                        );
//                        val isFollow = repository.getItemById(uid!!) != null
//
//                        list.add(
//                            AQIModel(
//                                uid,
//                                lat,
//                                long,
//                                nameAddress,
//                                address,
//                                aqiIndex,
//                                false,
//                                isFollow
//                            )
//                        )
//                    }
//                    return@map list
//                }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableSingleObserver<ArrayList<AQIModel>>() {
//                    override fun onSuccess(result: ArrayList<AQIModel>) {
//                        callback.onSuccess(result)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        callback.onError(e.message!!)
//                    }
//                })
//        )
//    }

//    fun destroy() {
//        disposable.dispose()
//    }

//    fun fetchNearestCurrenLocation(latLng: String, callback: ResultCallback): S{
//        apiService
//            .fetchNearestStation(latLng)
//        /*disposable.add(
//            apiService
//                .fetchNearestStation(latLng)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableSingleObserver<SearchResult>() {
//                    override fun onSuccess(result: SearchResult) {
//                        callback.onSuccess(result)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        callback.onError(e.message!!)
//                    }
//                })
//        )*/
//    }
//
//override fun fetchAllQAIData(listAQIModel: List<AQIModel>): Observable<List<AQIModel>> {
//    return Observable.fromIterable(listAQIModel.map { aqiModel ->
//        fetchNearestStation(aqiModel.getLatLngInString())
//    })
//        /*return Observable.fromIterable(listAQIModel.map { aqiModel ->
//            fetchNearestStation(aqiModel.getLatLngInString()).map returnPosition@{
//                    aqiModel.aqiIndex = it.allInfo?.aqi
//                    return@returnPosition aqiModel
//                }
//        }).flatMap {
//            it }
//            .toList()*/
//    }
//
    override fun fetchAllAqiData(listAQIModel: List<AQIModel>): Observable<List<AQIModel>> {
        return Observable.zip(listAQIModel.map { aqiModel ->
            return@map apiService
                .fetchNearestStation(aqiModel.getLatLngInString()).map returnPosition@{
                    aqiModel.aqiIndex = it.allInfo?.aqi
                    return@returnPosition aqiModel
                }
        }, Function<Array<Any>, List<AQIModel>> {
            it.toList() as List<AQIModel>
        })
    }
//
//    fun fetchAllAqiData2(listAQIModel: List<AQIModel>): Observable<List<AQIModel>> {
//        return Observable.zip(listAQIModel.map { aqiModel ->
//            apiService
//                .fetchNearestStation(aqiModel.getLatLngInString()).toObservable()
//        }, Function<Array<Any>, List<AQIModel>> {
//            it as List<AQIModel>
////            it.toList() as List<AQIModel>
//        })
//    }
}