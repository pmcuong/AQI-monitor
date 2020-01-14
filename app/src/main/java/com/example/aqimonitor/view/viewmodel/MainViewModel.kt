package com.example.aqimonitor.view.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.aqimonitor.base.BaseViewModel
import com.example.aqimonitor.model.AQIModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : BaseViewModel(application) {

    private var data = MutableLiveData<ArrayList<AQIModel>>()

    fun getAllData(): MutableLiveData<ArrayList<AQIModel>> {
        return data
    }

    fun getListAqi() {
        val tempo = appDataManager.getAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                var list = ArrayList<AQIModel>()
                list.addAll(it)
                data.postValue(list)
            })
        tempo?.let {
            disposable?.add(it)
        }
    }

    fun removeItem(aqiModel: AQIModel) {
        disposable?.add(
            appDataManager.deleteAqiModel(aqiModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                })
        )
    }

    fun addListItem(list: List<AQIModel>) {
        disposable?.add(
            appDataManager.insertListAqiModel(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }


    fun getNearestCurrentLocation(aqiModel: AQIModel) {
        disposable?.add(
            appDataManager.getAqiModelNearest(aqiModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    addItem(it)
                })
        )

    }

    fun updateAllData() {
        disposable?.add(appDataManager.getAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getAqiFromList(it as ArrayList<AQIModel>)
            })
        )
    }

    fun getAqiFromList(listAqiModel: ArrayList<AQIModel>) {
        disposable?.add(appDataManager.fetchAllAqiData(listAqiModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { result ->
                    var list = ArrayList<AQIModel>()
                    list.addAll(result)
                    insertListAqi(list)
                }
            )
        )
    }

    fun insertListAqi(list: ArrayList<AQIModel>) {
        disposable?.add(appDataManager.insertListAqiModel(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                data.postValue(list)
            }))
    }

    fun addItem(aqiModel: AQIModel) {
        disposable?.add(appDataManager.insertAqiModel(aqiModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getListAqi()
            })
        )
    }
}