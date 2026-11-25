package com.example.myweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.api.RetrofitClient
import com.example.myweatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface WeatherUiState {
    object Loading : WeatherUiState
    data class Success(
        val cityName: String,
        val weatherMain: String,
        val weatherDescription: String,
        val iconUrl: String,
        val iconCode: String
    ) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    // Default API Key provided by user
    var currentApiKey: String = "723ce809d20bc918a2a604a01921b690"
        private set

    init {
        fetchWeather("New York")
    }

    fun fetchWeather(cityName: String = "New York", apiKey: String = currentApiKey) {
        currentApiKey = apiKey
        _uiState.value = WeatherUiState.Loading

        viewModelScope.launch {
            try {
                val response = RetrofitClient.weatherService.getWeather(
                    cityName = cityName,
                    apiKey = apiKey
                )

                if (response.isSuccessful) {
                    val body: WeatherResponse? = response.body()
                    val weatherInfo = body?.weather?.firstOrNull()

                    if (body != null && weatherInfo != null) {
                        val name = body.name ?: cityName
                        val main = weatherInfo.main ?: "Unknown"
                        val description = weatherInfo.description ?: "No description"
                        val iconCode = weatherInfo.icon ?: "01d"
                        // OpenWeatherMap weather icon URL formula
                        val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"

                        _uiState.value = WeatherUiState.Success(
                            cityName = name,
                            weatherMain = main,
                            weatherDescription = description,
                            iconUrl = iconUrl,
                            iconCode = iconCode
                        )
                    } else {
                        _uiState.value = WeatherUiState.Error("No weather data found in server response.")
                    }
                } else {
                    val errorCode = response.code()
                    val errorMsg = when (errorCode) {
                        401 -> "Unauthorized (401): Invalid API key. Please check your OpenWeatherMap API key."
                        404 -> "City Not Found (404): Could not find weather data for '$cityName'."
                        else -> "Server error ($errorCode): ${response.message()}"
                    }
                    _uiState.value = WeatherUiState.Error(errorMsg)
                }
            } catch (e: IOException) {
                _uiState.value = WeatherUiState.Error("Network Error: Unable to connect to weather server. Please check your internet connection.")
            } catch (e: HttpException) {
                _uiState.value = WeatherUiState.Error("HTTP Error: ${e.message()}")
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error("Unexpected Error: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }
}
