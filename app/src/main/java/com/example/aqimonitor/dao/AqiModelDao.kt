package com.example.aqimonitor.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.utils.Constant

@Dao
interface AqiModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: AQIModel)

    @Delete
    fun deleteItem(item: AQIModel)

//    @Query("SELECT 1 FROM AQI_MONITOR_DATABASE WHERE id = '% :myId %'")
//    fun getItemById(myId: Int): Boolean

    @Query("SELECT * FROM AQI_MONITOR_DATABASE WHERE id=:id ")
    fun getItemById(id: Int): AQIModel

    @Query("SELECT * FROM AQI_MONITOR_DATABASE")
    fun getAllItems(): LiveData<List<AQIModel>>
}