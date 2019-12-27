package com.example.aqimonitor.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.model.MainItem
import com.example.aqimonitor.model.search.SearchGlobalObject
import com.example.aqimonitor.network.ApiManager
import com.example.aqimonitor.network.ResultCallback

class MainViewModel: ViewModel() {

    private val list: ArrayList<AQIModel> = ArrayList()

    val listData: MutableLiveData<List<AQIModel>> = MutableLiveData()

    fun setListData() {

        list.add(AQIModel("Ha Noi", "Ha Noi, Viet Nam", 340))
        list.add(AQIModel("Ha Noi", "Ha Noi, Viet Nam", 340))
        list.add(AQIModel("Ha Noi", "Ha Noi, Viet Nam", 240))
        list.add(AQIModel("Ha Noi", "Ha Noi, Viet Nam", 112))
        list.add(AQIModel("Ha Noi", "Ha Noi, Viet Nam", 87))
        list.add(AQIModel("Ha Noi", "Ha Noi, Viet Nam", 40))

        listData.value = list
    }

    fun searchCity(city: String) {
        ApiManager.instance.fetchCityByName(city, object : ResultCallback {
            override fun onError(errCode: String) {

            }
            override fun onSuccess(response: Any) {
                response as SearchGlobalObject
                Log.e("response", response.data?.get(0)?.station?.name.toString())
            }
        })
    }

}