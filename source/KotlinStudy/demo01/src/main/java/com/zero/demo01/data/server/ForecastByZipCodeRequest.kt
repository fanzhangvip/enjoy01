package com.zero.demo01.data.server

import android.util.Log
import com.google.gson.Gson
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.net.URL

class ForecastByZipCodeRequest(private val zipCode: Long, val gson: Gson = Gson()) {

    companion object {
        private const val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private const val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
        private const val COMPLETE_URL = "$URL&APPID=$APP_ID&zip="
    }

    fun execute(): ForecastResult {
        val url = COMPLETE_URL + zipCode
        AnkoLogger("Zero").info { url }
        val forecastJsonStr = URL(url).readText()
        AnkoLogger("Zero").info { forecastJsonStr }
        return gson.fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}