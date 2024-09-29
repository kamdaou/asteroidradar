package com.ampersand.data.service.remote

import com.ampersand.data.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface NasaApiService {

    @GET("neo/rest/v1/feed")
    suspend fun fetchAsteroids(
        @Query("start_date") startDate: String = LocalDate.now().toString(),
        @Query("end_date") endDate: String = LocalDate.now().plusDays(7).toString(),
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<String>


    @GET("planetary/apod")
    suspend fun getDayImage(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<String>
}
