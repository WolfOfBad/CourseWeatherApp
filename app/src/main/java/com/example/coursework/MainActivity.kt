package com.example.coursework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coursework.dto.CurrentWeather
import com.example.coursework.ui.screens.WeatherViewModel
import com.example.coursework.ui.theme.CourseworkTheme

class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CourseworkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    WeatherApp(viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
    val weather by viewModel.weather.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var cityName by remember { mutableStateOf("Izhevsk") }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(cityName)) }

    Scaffold(topBar = {
        TopAppBar(title = {
            TextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    cityName = it.text
                },
                placeholder = { Text("Введите город") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), // цвет нижней линии без фокуса
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 8.dp)
            )
        }, actions = {
            TextButton(
                onClick = {
                    if (cityName.isNotBlank()) {
                        viewModel.getWeatherForCity(cityName.trim())
                    }
                }) {
                Text("Поиск")
            }
        })
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when {
                isLoading -> Text("Загрузка...", fontSize = 16.sp)
                error.isNotEmpty() -> Text(
                    text = "Ошибка: $error", color = Color.Red, fontSize = 16.sp
                )

                weather != null -> {
                    WeatherCurrentCard(
                        current = weather!!.current
                    )
                }

                else -> Text("Введите город и нажмите Поиск", fontSize = 16.sp)
            }
        }
    })
}


@Composable
fun WeatherCurrentCard(
    current: CurrentWeather
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${current.temperature_2m}°C",
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 50.sp,
                )
                Text(
                    text = "Ощущается как ${current.apparent_temperature}°C",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Влажность: ${current.relative_humidity_2m} %", fontSize = 14.sp)
                Text("Давление: ${current.pressure_msl} hPa", fontSize = 14.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Облачность: ${current.cloud_cover} %", fontSize = 14.sp)
                Text("Осадки: ${current.precipitation} мм", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Ветер:", style = MaterialTheme.typography.titleMedium, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Скорость: ${current.wind_speed_10m} км/ч", fontSize = 14.sp)
            Text("Направление: ${current.wind_direction_10m}°", fontSize = 14.sp)
            Text("Порывы: ${current.wind_gusts_10m} км/ч", fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
