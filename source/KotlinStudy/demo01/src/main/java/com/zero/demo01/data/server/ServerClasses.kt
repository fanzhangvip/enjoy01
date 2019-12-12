package com.zero.demo01.data.server

//data class ForecastResult(val city: City, val list: List<Forecast>)
//data class City(val id: Long, val name: String, val coord: Coordinates, val country: String, val population: Int)
//data class Coordinates(val lon: Float, val lat: Float)
//data class Forecast(val dt: Long, val temp: Temperature, val pressure: Float, val humidity: Int,
//                    val weather: List<Weather>, val speed: Float, val deg: Int, val clouds: Int, val rain: Float)
//
//data class Temperature(val day: Float, val min: Float, val max: Float, val night: Float, val eve: Float, val morn: Float)
//data class Weather(val id: Long, val main: String, val description: String, val icon: String)

data class ForecastResult(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Forecast>,
    val message: Double
)

data class City(
    val coord: Coordinates,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)

data class Coordinates(
    val lat: Double,
    val lon: Double
)

data class Forecast(
    val clouds: Int,
    val deg: Int,
    val dt: Long,
    val humidity: Int,
    val pressure: Int,
    val speed: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temperature,
    val weather: List<Weather>
)

data class Temperature(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)