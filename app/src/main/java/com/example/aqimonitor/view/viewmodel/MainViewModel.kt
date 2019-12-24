package com.example.aqimonitor.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aqimonitor.model.MainItem

class MainViewModel: ViewModel() {

    private val list: ArrayList<MainItem> = ArrayList()

    val listData: MutableLiveData<List<MainItem>> = MutableLiveData()

    fun setListData() {

        list.add(MainItem("ONE"))
        list.add(MainItem("TWO"))
        list.add(MainItem("THREE"))
        list.add(MainItem("FOUR"))
        list.add(MainItem("FIVE"))
        list.add(MainItem("SIX"))
        list.add(MainItem("SEVEN"))
        list.add(MainItem("EIGHT"))
        list.add(MainItem("NINE"))
        list.add(MainItem("TEN"))

        listData.value = list
    }

    companion object {
        var STATIC_VALUE = 10
    }
}

fun getData() = "10"