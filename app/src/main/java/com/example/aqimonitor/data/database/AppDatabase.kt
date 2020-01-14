package com.example.aqimonitor.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aqimonitor.data.database.dao.AqiModelDao
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.utils.Constant

@Database(entities = [AQIModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun aqiModelDao(): AqiModelDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(mContext: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        mContext.applicationContext,
                        AppDatabase::class.java,
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