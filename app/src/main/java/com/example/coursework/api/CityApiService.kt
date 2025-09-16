package com.example.coursework.api

import com.example.coursework.dto.CityResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CityApiService {
    @Headers("X-Api-Key: sqa6qt2Hn0qn4XcaCj1WGQ==06TUIeVQMfcBTfN6")
    @GET("city")
    suspend fun getCityCoordinates(@Query("name") cityName: String): List<CityResponse>
}
