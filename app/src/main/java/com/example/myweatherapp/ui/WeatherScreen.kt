package com.example.myweatherapp.ui

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.example.myweatherapp.viewmodel.WeatherUiState
import com.example.myweatherapp.viewmodel.WeatherViewModel

// Custom Teal color for Top Bar as seen in Figure 1
private val HeaderTealColor = Color(0xFF0B5645)
private val LightBackgroundColor = Color(0xFFF7F7F7)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "New York Weather",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HeaderTealColor
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LightBackgroundColor)
        ) {
            when (val state = uiState) {
                is WeatherUiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = HeaderTealColor)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Fetching weather for New York...",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }

                is WeatherUiState.Success -> {
                    WeatherSuccessContent(
                        cityName = state.cityName,
                        weatherMain = state.weatherMain,
                        weatherDescription = state.weatherDescription,
                        iconUrl = state.iconUrl
                    )
                }

                is WeatherUiState.Error -> {
                    WeatherErrorContent(
                        errorMessage = state.message,
                        currentApiKey = viewModel.currentApiKey,
                        onRetry = { apiKey ->
                            viewModel.fetchWeather("New York", apiKey)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherSuccessContent(
    cityName: String,
    weatherMain: String,
    weatherDescription: String,
    iconUrl: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = cityName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF222222)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = weatherMain,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF444444)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = weatherDescription,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF555555)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Using Glide to fetch and display the weather icon
        AndroidView(
            factory = { context ->
                ImageView(context).apply {
                    scaleType = ImageView.ScaleType.FIT_CENTER
                }
            },
            update = { imageView ->
                Glide.with(imageView.context)
                    .load(iconUrl)
                    .into(imageView)
            },
            modifier = Modifier.size(64.dp)
        )
    }
}

@Composable
fun WeatherErrorContent(
    errorMessage: String,
    currentApiKey: String,
    onRetry: (String) -> Unit
) {
    var inputKey by remember { mutableStateOf(currentApiKey) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Unable to Load Weather",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB00020)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = errorMessage,
            fontSize = 15.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = inputKey,
            onValueChange = { inputKey = it },
            label = { Text("API Key") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onRetry(inputKey) },
            colors = ButtonDefaults.buttonColors(containerColor = HeaderTealColor)
        ) {
            Text("Retry Request", color = Color.White)
        }
    }
}
