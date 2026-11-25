package com.example.myweatherapp

import com.example.myweatherapp.model.WeatherResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class WeatherParsingTest {

    @Test
    fun testWeatherResponseJsonParsing() {
        val sampleJson = """
            {
              "coord": { "lon": -74.006, "lat": 40.7143 },
              "weather": [
                {
                  "id": 803,
                  "main": "Clouds",
                  "description": "broken clouds",
                  "icon": "04d"
                }
              ],
              "base": "stations",
              "main": {
                "temp": 285.51,
                "feels_like": 284.7,
                "temp_min": 283.47,
                "temp_max": 287.21,
                "pressure": 1013,
                "humidity": 65
              },
              "visibility": 10000,
              "name": "New York",
              "cod": 200
            }
        """.trimIndent()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val adapter = moshi.adapter(WeatherResponse::class.java)
        val response = adapter.fromJson(sampleJson)

        assertNotNull(response)
        assertEquals("New York", response?.name)
        assertNotNull(response?.weather)
        assertEquals(1, response?.weather?.size)

        val weatherInfo = response?.weather?.first()
        assertEquals("Clouds", weatherInfo?.main)
        assertEquals("broken clouds", weatherInfo?.description)
        assertEquals("04d", weatherInfo?.icon)
    }
}
