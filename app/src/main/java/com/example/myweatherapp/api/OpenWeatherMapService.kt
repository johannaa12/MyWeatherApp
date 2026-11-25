package com.example.myweatherapp.api

import com.example.myweatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") cityName: String = "New York",
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>
}
