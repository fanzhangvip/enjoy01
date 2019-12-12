package com.zero.demo01.data.db

import com.zero.demo01.domain.datasource.ForecastDataSource
import com.zero.demo01.domain.model.ForecastList
import com.zero.demo01.extensions.clear
import com.zero.demo01.extensions.toVarargArray
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import kotlin.collections.HashMap
import com.zero.demo01.extensions.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ForecastDb(private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 private val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        AnkoLogger("Zero").info { "requestForecastByZipCode zipCode: $zipCode, date: $date" }
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        AnkoLogger("Zero").info{"dailyRequest: $dailyRequest"}
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }
        AnkoLogger("Zero").info{"dailyForecast: $dailyForecast"}

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        AnkoLogger("Zero").info{"city: $city"}
        city?.let { dataMapper.convertToDomain(it) }
    }

    override fun requestDayForecast(id: Long) = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME).byId(id).
                parseOpt { DayForecast(HashMap(it)) }

        forecast?.let { dataMapper.convertDayToDomain(it) }
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {
        AnkoLogger("Zero").info { "saveForecast forecast: $forecast" }
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}
