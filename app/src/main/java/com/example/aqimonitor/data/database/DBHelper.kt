package com.example.aqimonitor.data.database

import com.example.aqimonitor.model.AQIModel

import io.reactivex.Observable
import io.reactivex.Completable
import io.reactivex.Single

interface DBHelper {

    fun insertAqiModel(aqiModel: AQIModel): Completable

    fun insertListAqiModel(list: List<AQIModel>): Completable

    fun deleteAqiModel(aqiModel: AQIModel): Completable

    fun getAllData(): Single<List<AQIModel>>

    fun getItemById(id: Int):  Observable<AQIModel?>
}