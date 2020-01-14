package com.example.aqimonitor.data.database.dao

import androidx.room.*
import com.example.aqimonitor.model.AQIModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AqiModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: AQIModel): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListItem(list: List<AQIModel>): Completable

    @Delete
    fun deleteItem(item: AQIModel): Completable

//    @Query("SELECT 1 FROM AQI_MONITOR_DATABASE WHERE id = '% :myId %'")
//    fun getItemById(myId: Int): Boolean

    @Query("SELECT * FROM AQI_MONITOR_DATABASE WHERE id=:id ")
    fun getItemById(id: Int): AQIModel?

    @Query("SELECT * FROM AQI_MONITOR_DATABASE")
    fun getAllItems():  Observable<List<AQIModel>>
}