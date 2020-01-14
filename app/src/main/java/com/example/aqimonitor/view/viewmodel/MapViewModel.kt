package com.example.aqimonitor.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aqimonitor.base.BaseViewModel
import com.example.aqimonitor.model.AQIModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MapViewModel(application: Application) : BaseViewModel(application) {
    private var data = MutableLiveData<ArrayList<AQIModel>>()

    fun getAllData(): MutableLiveData<ArrayList<AQIModel>> {
        return data
    }

    fun updateAllData() {
        disposable?.add(appDataManager.getAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getAqiFromList(it as ArrayList<AQIModel>)
            }))
    }

    fun getAqiFromList(listAqiModel: ArrayList<AQIModel>) {
        disposable?.add(appDataManager.fetchAllAqiData(listAqiModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { result ->
                    var list = ArrayList<AQIModel>()
                    list.addAll(result)
                    data.postValue(list)
                }
            )
        )
    }

    fun getAllFromDB(){
        disposable?.add(appDataManager.getAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                for (item in it) {
                    Log.d("MapViewModel", ": ${item.nameAddress}, ${item.aqiIndex}");
                }
                var list = ArrayList<AQIModel>()
                list.addAll(it)
                data.postValue(list)
            }))
    }

    fun
            addItem(aqiModel: AQIModel) {
        disposable?.add(appDataManager.insertAqiModel(aqiModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getListAqi()
            }))
    }

    fun getListAqi() {
        disposable?.add( appDataManager.getAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                data.postValue(it as ArrayList<AQIModel>)
        }))
    }

    fun getNearestCurrentLocation(aqiModel: AQIModel) {
        disposable?.add(appDataManager.getAqiModelNearest(aqiModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                addItem(it)
            })
        )

    }
}