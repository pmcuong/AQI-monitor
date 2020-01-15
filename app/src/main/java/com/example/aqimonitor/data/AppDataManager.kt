package com.example.aqimonitor.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.Transformations.map
import com.example.aqimonitor.data.database.DBHelper
import com.example.aqimonitor.data.network.ApiHelper
import com.example.aqimonitor.data.network.model.air_quality.SearchResult
import com.example.aqimonitor.data.network.model.search.SearchGlobalObject
import com.example.aqimonitor.data.network.model.search.SearchLocationObject
import com.example.aqimonitor.extention.getLatLngInString
import com.example.aqimonitor.model.AQIModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class AppDataManager(context: Context, dbHelper: DBHelper, apiHelper: ApiHelper) : DataManager {

    private var context: Context? = context
    private var dbHelper: DBHelper? = dbHelper
    private var apiHelper: ApiHelper? = apiHelper

    override fun insertAqiModel(aqiModel: AQIModel): Completable {
        return dbHelper?.insertAqiModel(aqiModel)!!
    }

    override fun insertListAqiModel(list: List<AQIModel>): Completable {
        return dbHelper?.insertListAqiModel(list)!!
    }

    override fun deleteAqiModel(aqiModel: AQIModel): Completable {
        return dbHelper?.deleteAqiModel(aqiModel)!!
    }

    override fun getAllData(): Single<List<AQIModel>> {
        return dbHelper?.getAllData()!!
    }

    override fun getItemById(id: Int): Observable<AQIModel?> {
        return dbHelper?.getItemById(id)!!
    }

    override fun fetchCityID(city: String): Observable<SearchGlobalObject> {
        return apiHelper?.fetchCityID(city)!!
    }

    override fun fetchListAqiFromCity(city: String): Observable<List<AQIModel>> {
        return fetchCityID(city)
            .flatMap { data -> Observable.fromIterable(data.data) }
            .flatMap { result ->
                Observable.zip(
                    dbHelper?.getItemById(result.uid!!),
                    Observable.just(result),
                    BiFunction<AQIModel?, SearchLocationObject, AQIModel> { aqiModel, data ->
                        // here we get both the results at a time.
                        var isFollow = false
                        if (aqiModel.id > 0) {
                            isFollow = true
                        }
                        return@BiFunction AQIModel(
                            data.uid!!,
                            data.station?.geo?.get(0),
                            data.station?.geo?.get(1),
                            data.station?.getNameAddress(),
                            data.station?.getFullAddress(),
                            data.getAqiInFloat(),
                            false,
                            isFollow
                        )
                    }
                )
            }.toList().toObservable()

    }


    override fun fetchNearestStation(latLng: String): Observable<SearchResult> {
        return apiHelper?.fetchNearestStation(latLng)!!
    }

    override fun getAqiModelNearest(aqiModel: AQIModel): Observable<AQIModel> {
        return apiHelper?.fetchNearestStation(aqiModel.getLatLngInString())!!.map {
            aqiModel.aqiIndex = it.allInfo?.aqi
            return@map aqiModel


        }
    }

    override fun fetchAllAqiData(listAQIModel: List<AQIModel>): Observable<List<AQIModel>> {
        return apiHelper?.fetchAllAqiData(listAQIModel)!!
    }

}

