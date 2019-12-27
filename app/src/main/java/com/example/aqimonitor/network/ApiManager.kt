package com.example.aqimonitor.network

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

    fun fetchCityByName(name: String, callback: ResultCallback) {
        disposable.add(
            apiService
                .fetchCityID(city = name.trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchGlobalObject>() {
                    override fun onSuccess(result: SearchGlobalObject) {
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

}