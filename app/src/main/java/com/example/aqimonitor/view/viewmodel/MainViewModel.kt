package com.example.aqimonitor.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.model.MainItem

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

    companion object {
        var STATIC_VALUE = 10
    }
}

fun getData() = "10"