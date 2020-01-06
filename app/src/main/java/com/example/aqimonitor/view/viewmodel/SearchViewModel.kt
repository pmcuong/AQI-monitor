package com.example.aqimonitor.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.model.search.SearchGlobalObject
import com.example.aqimonitor.network.ApiManager
import com.example.aqimonitor.network.ResultCallback

class SearchViewModel: ViewModel() {
    private val list: ArrayList<AQIModel> = ArrayList()

    val listData: MutableLiveData<List<AQIModel>> = MutableLiveData()

    fun searchCity(city: String) {
        ApiManager.instance.fetchCityByName(city, object : ResultCallback {
            override fun onError(errCode: String) {
                Log.d("SearchViewModel", "onError: $errCode");
            }

            override fun onSuccess(response: Any) {
                Log.d("SearchViewModel", "onSuccess: $response");
                response as SearchGlobalObject
                Log.d("SearchViewModel", "response: ${response.data}");
                response.data?.let {
                    list.clear()
                    for (item in response.data!!) {
                        val lat = item.station?.geo?.get(0)
                        val long = item.station?.geo?.get(1)
                        val nameAddress = item.station?.name
                        val address = item.station?.name
                        val aqiIndex = item.getAqiInFloat()
                        list.add(AQIModel(lat, long, nameAddress, address, aqiIndex, false ))
                    }
                    listData.postValue(list)
                }
            }
        })
    }

    fun setFollowItem(position: Int, isFollow: Boolean) {
        list[position].isFollow = isFollow
        listData.postValue(list)
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