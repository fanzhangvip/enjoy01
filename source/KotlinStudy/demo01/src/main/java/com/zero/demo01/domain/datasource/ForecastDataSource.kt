package com.zero.demo01.domain.datasource

import com.zero.demo01.domain.model.Forecast
import com.zero.demo01.domain.model.ForecastList


interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?

}