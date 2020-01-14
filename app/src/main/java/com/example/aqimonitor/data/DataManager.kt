package com.example.aqimonitor.data

import com.example.aqimonitor.data.database.DBHelper
import com.example.aqimonitor.data.network.ApiHelper
import com.example.aqimonitor.data.network.ApiService

interface DataManager : DBHelper, ApiHelper {

}