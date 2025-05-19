package com.example.coursework.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.dto.WeatherResponse
import com.example.coursework.dto.CityResponse
import com.example.coursework.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather = _weather.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getWeatherForCity("Izhevsk")
    }

    fun getWeatherForCity(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = ""
            try {
                val cityInfo: List<CityResponse> = RetrofitInstance.cityApi.getCityCoordinates(city)
                val firstCity = cityInfo.firstOrNull()
                if (firstCity != null) {
                    val forecast = RetrofitInstance.weatherApi.getWeather(
                        latitude = firstCity.latitude,
                        longitude = firstCity.longitude
                    )
                    _weather.value = forecast
                } else {
                    _error.value = "Город не найден"
                    _weather.value = null
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Неизвестная ошибка"
                _weather.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}

