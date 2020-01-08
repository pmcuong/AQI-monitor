package com.example.aqimonitor.database

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.example.aqimonitor.dao.AqiModelDao
import com.example.aqimonitor.model.AQIModel

class AqiRepository(application: Application) {
    private var aqiModelDao: AqiModelDao?=null

    private var allData : LiveData<List<AQIModel>>? = null

    init {
        val database: AqiDatabase = AqiDatabase.getInstance(
            application.applicationContext
        )!!
        aqiModelDao = database.aqiModelDao()
        allData = aqiModelDao?.getAllItems()
    }

    fun insertAqiModel(aqiModel: AQIModel) {
        InsertItemAsyncTask(aqiModelDao!!).execute(aqiModel)
    }

    fun deleteAqiModel(aqiModel: AQIModel) {
        DeleteItemAsyncTask(aqiModelDao!!).execute(aqiModel)
    }

    fun getAllData(): LiveData<List<AQIModel>> {
        return allData!!
    }

    fun getItemById(id: Int): AQIModel {
        return aqiModelDao!!.getItemById(id)
    }

    private class InsertItemAsyncTask(aqiModelDao: AqiModelDao) : AsyncTask<AQIModel, Unit, Unit>() {
        override fun doInBackground(vararg params: AQIModel?) {
            aqiModelDao.insertItem(params[0]!!)
        }

        val aqiModelDao = aqiModelDao
    }

    private class DeleteItemAsyncTask(aqiModelDao: AqiModelDao) : AsyncTask<AQIModel, Unit, Unit>() {
        val aqiModelDao = aqiModelDao
        override fun doInBackground(vararg params: AQIModel?) {
            aqiModelDao.deleteItem(params[0]!!)
        }

    }
}