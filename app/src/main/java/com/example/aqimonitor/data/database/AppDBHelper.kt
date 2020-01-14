package com.example.aqimonitor.data.database

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.aqimonitor.data.database.dao.AqiModelDao
import com.example.aqimonitor.data.network.AppApiHelper
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.utils.Constant
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.util.concurrent.Callable

class AppDBHelper(application: Application) : DBHelper {
    companion object {
        var INSTANCE: AppDBHelper? = null
        fun getInstance(application: Application): AppDBHelper? {
            if (AppDBHelper.INSTANCE == null) {
                synchronized(AppDBHelper::class.java) {
                    INSTANCE = AppDBHelper(application)
                }
            }
            return INSTANCE
        }
    }

    override fun insertAqiModel(aqiModel: AQIModel): Completable {
        Log.d("AppDBHelper", ": $aqiModel");
        return aqiModelDao!!.insertItem(aqiModel)
    }

    override fun insertListAqiModel(list: List<AQIModel>): Completable {
        Log.d("AppDBHelper", ": ${list.size}");
        return aqiModelDao!!.insertListItem(list)
    }

    override fun deleteAqiModel(aqiModel: AQIModel): Completable {
        return aqiModelDao!!.deleteItem(aqiModel)
    }

    override fun getAllData(): Observable<List<AQIModel>> {
        return aqiModelDao!!.getAllItems()
    }

    override fun getItemById(id: Int):  Observable<AQIModel?> {
        return Observable.fromCallable(object : Callable<AQIModel?>{
            override fun call(): AQIModel? {
                var aqiModel = aqiModelDao?.getItemById(id)
                if (aqiModel == null) {
                    aqiModel = AQIModel(-1, 0.0, 0.0)
                }
                return aqiModel
            }

        })
    }

    private var aqiModelDao: AqiModelDao? = null

    private var allData: LiveData<List<AQIModel>>? = null

    init {
        val database: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        aqiModelDao = database.aqiModelDao()
//        allData = aqiModelDao?.getAllItems()
    }
}