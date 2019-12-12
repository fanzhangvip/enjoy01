package com.zero.demo01.domain.datasource

import com.zero.demo01.data.db.ForecastDb
import com.zero.demo01.data.server.ForecastServer
import com.zero.demo01.domain.model.Forecast
import com.zero.demo01.domain.model.ForecastList
import com.zero.demo01.extensions.firstResult
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ForecastProvider(private val sources: List<ForecastDataSource> = SOURCES) {

    companion object {
        const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf(ForecastDb(), ForecastServer()) }
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        AnkoLogger("Zero").info { "requestByZipCode(zipCode: $zipCode, days: $days)" }
        val date = todayTimeSpan()
        AnkoLogger("Zero").info { "requestByZipCode date = $date" }
        val res = it.requestForecastByZipCode(zipCode, date)
        if (res != null && res.size >= days) res else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources { it.requestDayForecast(id) }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T = sources.firstResult { f(it) }

}