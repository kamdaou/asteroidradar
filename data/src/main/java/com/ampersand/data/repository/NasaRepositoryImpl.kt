package com.ampersand.data.repository

import com.ampersand.data.service.NasaApiService
import com.ampersand.domain.repository.NasaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NasaRepositoryImpl @Inject constructor(
    private val nasaApiService: NasaApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NasaRepository {
    override suspend fun fetchAsteroids(): String =
        withContext(ioDispatcher) {
            val response = nasaApiService.fetchAsteroids()
            return@withContext response.links.next
        }
}
