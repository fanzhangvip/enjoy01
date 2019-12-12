package com.zero.demo01.data.server


import com.zero.demo01.data.db.ForecastDb
import com.zero.demo01.domain.datasource.ForecastDataSource
import com.zero.demo01.domain.model.ForecastList
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        AnkoLogger("Zero").info { "requestForecastByZipCode zipCode: $zipCode, date: $date" }
        val result = ForecastByZipCodeRequest(zipCode).execute()
        AnkoLogger("Zero").info { "result: $result" }
        val converted = dataMapper.convertToDomain(zipCode, result)
        AnkoLogger("Zero").info { "converted: $converted" }
        forecastDb.saveForecast(converted)
        val dbresult = forecastDb.requestForecastByZipCode(zipCode,date)
        AnkoLogger("Zero").info { "dbresult: $dbresult" }
        return dbresult
    }

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()
}