package com.zero.demo01.domain.model

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

data class ForecastList(val id: Long, val city: String, val country: String,
        val dailyForecast: List<Forecast>) {
    init {
        AnkoLogger("Zero").info { "ForecastList init id: $id, city: $city, country: $country, dailyForecast: $dailyForecast" }
    }

    val size: Int
        get() = dailyForecast.size

    operator fun get(position: Int) = dailyForecast[position]
}

data class Forecast(val id: Long, val date: Long, val description: String, val high: Int, val low: Int,
                    val iconUrl: String){
    init {
        AnkoLogger("Zero").info{"Forecast init id: $id, date: $date, description: $description, high: $high, low: $low, iconUrl: $iconUrl"}
    }
}