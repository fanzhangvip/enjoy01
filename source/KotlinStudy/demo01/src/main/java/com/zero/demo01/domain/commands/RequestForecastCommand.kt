package com.zero.demo01.domain.commands

import com.zero.demo01.domain.datasource.ForecastProvider
import com.zero.demo01.domain.model.ForecastList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestForecastCommand(
        private val zipCode: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    init {
        println("RequestForecastCommand init zipCode: $zipCode, forecastProvider: $forecastProvider")
    }

    companion object {
        const val DAYS = 7
    }

    override suspend fun execute() = withContext(Dispatchers.IO) {
        forecastProvider.requestByZipCode(zipCode, DAYS)
    }
}