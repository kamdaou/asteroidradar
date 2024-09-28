package com.ampersand.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ampersand.data.service.remote.NasaApiService
import com.ampersand.domain.repository.NasaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NasaRepositoryImpl @Inject constructor(
    private val nasaApiService: NasaApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NasaRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchAsteroids(): String =
        withContext(ioDispatcher) {
            val response = nasaApiService.fetchAsteroids()
            Log.i("Repository", "response: ${response.body()}")
            return@withContext response.body().toString()
        }
}
