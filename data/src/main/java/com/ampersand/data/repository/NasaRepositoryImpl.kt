package com.ampersand.data.repository

import com.ampersand.core.ApiResult
import com.ampersand.data.service.remote.NasaApiService
import com.ampersand.data.utils.parseAsteroidsJsonResult
import com.ampersand.domain.model.AsteroidModel
import com.ampersand.domain.repository.NasaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class NasaRepositoryImpl @Inject constructor(
    private val nasaApiService: NasaApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NasaRepository {

    override suspend fun fetchAsteroids(): ApiResult<List<AsteroidModel>> =
        withContext(ioDispatcher) {
            try {
                val response = nasaApiService.fetchAsteroids()
                if (response.isSuccessful && response.body() != null) {
                    val asteroidJson = JSONObject(response.body())
                    val asteroidList = parseAsteroidsJsonResult(asteroidJson)
                    return@withContext ApiResult.Success(asteroidList)
                } else {
                    return@withContext ApiResult.Error()
                }
            } catch (e: Exception) {
                return@withContext ApiResult.Error()
            }
        }
}
