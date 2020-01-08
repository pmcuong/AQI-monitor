package com.example.aqimonitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aqimonitor.dao.AqiModelDao
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.utils.Constant

@Database(entities = [AQIModel::class], version = 1)
abstract class AqiDatabase : RoomDatabase() {

    abstract fun aqiModelDao(): AqiModelDao

    companion object {
        private var instance: AqiDatabase? = null

        fun getInstance(mContext: Context): AqiDatabase? {
            if (instance == null) {
                synchronized(AqiDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        mContext.applicationContext,
                        AqiDatabase::class.java,
                        Constant.AQI_DATABASE
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}