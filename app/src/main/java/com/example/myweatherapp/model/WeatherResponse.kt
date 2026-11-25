package com.example.myweatherapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class WeatherResponse(
    @Json(name = "name") val name: String? = null,
    @Json(name = "weather") val weather: List<WeatherInfo>? = null,
    @Json(name = "main") val main: MainInfo? = null,
    @Json(name = "cod") val cod: Any? = null,
    @Json(name = "message") val message: String? = null
)

@JsonClass(generateAdapter = false)
data class WeatherInfo(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "main") val main: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "icon") val icon: String? = null
)

@JsonClass(generateAdapter = false)
data class MainInfo(
    @Json(name = "temp") val temp: Double? = null,
    @Json(name = "feels_like") val feelsLike: Double? = null,
    @Json(name = "temp_min") val tempMin: Double? = null,
    @Json(name = "temp_max") val tempMax: Double? = null,
    @Json(name = "pressure") val pressure: Int? = null,
    @Json(name = "humidity") val humidity: Int? = null
)
