package com.example.aqimonitor.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aqimonitor.database.AqiRepository
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.model.search.SearchGlobalObject
import com.example.aqimonitor.network.ApiManager
import com.example.aqimonitor.network.ResultCallback

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private var list: ArrayList<AQIModel> = ArrayList()

    private val repository = AqiRepository(application)

    val listDataSearch: MutableLiveData<List<AQIModel>> = MutableLiveData()
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

    fun searchCity(city: String) {
        ApiManager.instance.fetchCityByName(city, object : ResultCallback {
            override fun onError(errCode: String) {
                Log.d("SearchViewModel", "onError: $errCode");
            }

            override fun onSuccess(response: Any) {
                response as ArrayList<AQIModel>
                response?.let {
                    list = response
                    listDataSearch.postValue(list)
                }
            }
        }, repository)
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