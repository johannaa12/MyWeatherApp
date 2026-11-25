package com.example.myweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myweatherapp.ui.WeatherScreen
import com.example.myweatherapp.ui.WeatherSuccessContent
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWeatherAppTheme {
                WeatherScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    MyWeatherAppTheme {
        WeatherSuccessContent(
            cityName = "New York",
            weatherMain = "Clouds",
            weatherDescription = "broken clouds",
            iconUrl = "https://openweathermap.org/img/wn/04d@2x.png"
        )
    }
}
