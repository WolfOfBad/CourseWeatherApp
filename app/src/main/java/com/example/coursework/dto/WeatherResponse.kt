package com.example.coursework.dto

data class WeatherResponse(
    val latitude: Double, val longitude: Double, val current: CurrentWeather
)

data class CurrentWeather(
    val time: String,
    val temperature_2m: Double,
    val relative_humidity_2m: Int,
    val apparent_temperature: Double,
    val is_day: Int,
    val showers: Double,
    val rain: Double,
    val snowfall: Double,
    val cloud_cover: Int,
    val weather_code: Int,
    val pressure_msl: Double,
    val surface_pressure: Double,
    val wind_speed_10m: Double,
    val wind_direction_10m: Int,
    val wind_gusts_10m: Double,
    val precipitation: Double
)