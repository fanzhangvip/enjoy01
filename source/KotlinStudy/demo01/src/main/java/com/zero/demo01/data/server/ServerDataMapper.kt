package com.zero.demo01.data.server

import com.zero.demo01.domain.model.ForecastList
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*
import java.util.concurrent.TimeUnit
import com.zero.demo01.domain.model.Forecast as ModelForecast

class ServerDataMapper {

    fun convertToDomain(zipCode: Long, forecast: ForecastResult) = with(forecast) {
        AnkoLogger("Zero").info { "convertToDomain zipCode: $zipCode, forecast: $forecast" }
        ForecastList(zipCode, city.name, city.country, convertForecastListToDomain(list))
    }

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        AnkoLogger("Zero").info { "convertForecastListToDomain list: $list" }
        return list.mapIndexed { i, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            AnkoLogger("Zero").info { "i $i forecast: $forecast, dt: $dt" }
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }

    private fun convertForecastItemToDomain(forecast: Forecast) = with(forecast) {
        AnkoLogger("Zero").info { "convertForecastItemToDomain forecast: $forecast" }
        ModelForecast(-1, dt, weather[0].description, temp.max.toInt(), temp.min.toInt(),
                generateIconUrl(weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}