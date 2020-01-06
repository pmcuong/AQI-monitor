package com.example.aqimonitor.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aqimonitor.extention.getLatLngInString
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.model.MainItem
import com.example.aqimonitor.model.air_quality.SearchResult
import com.example.aqimonitor.model.search.SearchGlobalObject
import com.example.aqimonitor.network.ApiManager
import com.example.aqimonitor.network.ResultCallback

class MainViewModel : ViewModel() {

    private val list: ArrayList<AQIModel> = ArrayList()

    val listData: MutableLiveData<List<AQIModel>> = MutableLiveData()

    fun setListData() {

    }

    fun addItem(aqiModel: AQIModel) {
        if (aqiModel.isCurrentPosition && list.size > 0 && list[0].isCurrentPosition) {
            list.removeAt(0)
            list.add(0, aqiModel)
        } else {
            list.add(aqiModel)
        }
        listData.value = list
    }

    fun getNearestCurrentLocation(aqiModel: AQIModel) {
        ApiManager.instance.fetchNearestCurrenLocation(aqiModel.getLatLngInString(), object : ResultCallback {
            override fun onError(errCode: String) {

            }

            override fun onSuccess(response: Any) {
                when (response) {
                    is SearchGlobalObject -> {
                        Log.e("response", response.data?.get(0)?.station?.name.toString())
                    }
                    is SearchResult -> {
                        aqiModel.aqiIndex = response.allInfo.aqi
                        addItem(aqiModel)
                    }
                }

            }
        })
    }

    fun updateList(listAqiModel: ArrayList<AQIModel>) {
        list.addAll(listAqiModel)
        listData.postValue(list)
    }
}