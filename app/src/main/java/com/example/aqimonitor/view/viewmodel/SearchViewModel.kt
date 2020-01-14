package com.example.aqimonitor.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aqimonitor.base.BaseViewModel
import com.example.aqimonitor.data.database.AppDBHelper
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.data.network.AppApiHelper
import com.example.aqimonitor.data.network.ResultCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class SearchViewModel(application: Application): BaseViewModel(application) {
    private var list: ArrayList<AQIModel> = ArrayList()

    val listDataSearch: MutableLiveData<List<AQIModel>> = MutableLiveData()
    val listData = MutableLiveData<List<AQIModel>>()

    fun addItem(aqiModel: AQIModel) {
        disposable?.add(appDataManager.insertAqiModel(aqiModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }))
    }

    fun removeItem(aqiModel: AQIModel) {
        disposable?.add(appDataManager.deleteAqiModel(aqiModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }))
    }

    fun searchCity(city: String) {
        disposable?.add(appDataManager.fetchListAqiFromCity(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("SearchViewmodel", ": " + it);
                list.clear()
                list.addAll(it)
                listDataSearch.postValue(list)
            },  {
                Log.d("SearchViewmodel", ": " + it);
            }))
    }

    fun setFollowItem(position: Int, isFollow: Boolean) {
        list[position].isFollow = isFollow
        listDataSearch.postValue(list)
    }

    fun getFollowedList(): ArrayList<AQIModel> {
        val followedList: ArrayList<AQIModel> = ArrayList()
        for (aqiModel in list) {
            if (aqiModel.isFollow) {
                followedList.add(aqiModel)
            }
        }
        return followedList
    }
}