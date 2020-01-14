package com.example.aqimonitor.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.aqimonitor.data.AppDataManager
import com.example.aqimonitor.data.database.AppDBHelper
import com.example.aqimonitor.data.network.AppApiHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var disposable: CompositeDisposable? = null
    var appDataManager: AppDataManager

    init {
        disposable = CompositeDisposable()
        appDataManager = AppDataManager(application, AppDBHelper.getInstance(application)!!, AppApiHelper.instance)
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}