package com.example.aqimonitor.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.aqimonitor.base.BaseViewModel
import com.example.aqimonitor.model.AQIModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val TAG = "MainViewModel"
    private var data = MutableLiveData<ArrayList<AQIModel>>()

    fun getAllData(): MutableLiveData<ArrayList<AQIModel>> {
        return data
    }

    fun getListAqi() {
        Log.d("MainViewModel", "getListAqi");
        disposable?.add(appDataManager.getAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("MainViewModel", ": $it")
                var list = ArrayList<AQIModel>()
                list.addAll(it)
                data.postValue(list)
            },{})
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
        Log.d(TAG, "updateAllData ");
        var myDisposable: Disposable = appDataManager.getAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "updateAllData: " + it.size);
                getAqiFromList(it as ArrayList<AQIModel>)
            },{})
        myDisposable?.let {
            disposable?.add(it)
        }
    }

    fun getAqiFromList(listAqiModel: ArrayList<AQIModel>) {
        Log.d(TAG, "getAqiFromList ");
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
        Log.d(TAG, "insertListAqi ");
        disposable?.add(appDataManager.insertListAqiModel(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                data.postValue(list)
            })
        )
    }

    fun addItem(aqiModel: AQIModel) {
        Log.d("MainViewModel", "addItem");
        disposable?.add(
            appDataManager.insertAqiModel(aqiModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getListAqi()
                })
        )
    }

    fun removeItem(aqiModel: AQIModel) {
        disposable?.add(
            appDataManager.deleteAqiModel(aqiModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getListAqi()
                })
        )
    }
}