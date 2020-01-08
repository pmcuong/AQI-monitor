package com.example.aqimonitor.view.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aqimonitor.database.AqiDatabase
import com.example.aqimonitor.database.AqiRepository
import com.example.aqimonitor.extention.getLatLngInString
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.model.air_quality.SearchResult
import com.example.aqimonitor.model.search.SearchGlobalObject
import com.example.aqimonitor.network.ApiManager
import com.example.aqimonitor.network.ResultCallback

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val list: ArrayList<AQIModel> = ArrayList()

    private val repository = AqiRepository(application)

    val listData: LiveData<List<AQIModel>> = repository.getAllData()

    fun addItem(aqiModel: AQIModel) {
        repository.insertAqiModel(aqiModel)
    }

    fun removeItem(aqiModel: AQIModel) {
        repository.deleteAqiModel(aqiModel)
    }

    fun getItemBytId(id: Int): AQIModel{
        return repository.getItemById(id)
    }

    fun getNearestCurrentLocation(aqiModel: AQIModel) {
        Log.d("MainViewModel", "onSuccess: ${aqiModel.address}, " + aqiModel.aqiIndex);
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
//        list.addAll(listAqiModel)
//        listData.postValue(list)
    }
}