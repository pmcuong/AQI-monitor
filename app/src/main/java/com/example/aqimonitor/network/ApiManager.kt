package com.example.aqimonitor.network

import android.util.Log
import com.example.aqimonitor.database.AqiRepository
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.model.air_quality.SearchResult
import com.example.aqimonitor.model.search.SearchGlobalObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ApiManager {

    private var apiService: ApiService = ApiClient.client.create(ApiService::class.java)
    private var disposable: CompositeDisposable = CompositeDisposable()

    private object HOLDER {
        val INSTANCE = ApiManager()
    }

    companion object {
        val instance: ApiManager by lazy {
            HOLDER.INSTANCE
        }
    }

    fun fetchCityByName(name: String, callback: ResultCallback, repository: AqiRepository) {
        disposable.add(
            apiService
                .fetchCityID(city = name.trim())
                .map {response ->
                    val list: ArrayList<AQIModel> = ArrayList()
                    for (item in response.data!!) {
                        val lat = item.station?.geo?.get(0)
                        val long = item.station?.geo?.get(1)
                        val nameAddress = item.station?.name
                        val address = item.station?.name
                        val aqiIndex = item.getAqiInFloat()
                        val uid = item.uid
                        Log.d("ApiManager", "onSuccess: " + uid + ", " + repository.getItemById(uid!!));
                        val isFollow = repository.getItemById(uid!!) != null

                        list.add(AQIModel(uid, lat, long, nameAddress, address, aqiIndex, false, isFollow ))
                    }
                    return@map list
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<AQIModel>>() {
                    override fun onSuccess(result: ArrayList<AQIModel>) {
                        callback.onSuccess(result)
                    }

                    override fun onError(e: Throwable) {
                        callback.onError(e.message!!)
                    }
                }))
    }

    fun destroy() {
        disposable.dispose()
    }

    fun fetchNearestCurrenLocation(latLng: String, callback: ResultCallback) {
        disposable.add(
            apiService
                .fetchNearestStation(latLng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchResult>() {
                    override fun onSuccess(result: SearchResult) {
                        callback.onSuccess(result)
                    }

                    override fun onError(e: Throwable) {
                        callback.onError(e.message!!)
                    }
                }))
    }
}