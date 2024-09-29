package com.ampersand.data.repository

import android.util.Log
import com.ampersand.core.ApiResult
import com.ampersand.core.R
import com.ampersand.core.UiText
import com.ampersand.data.service.local.AsteroidDao
import com.ampersand.data.service.local.entitiy.AsteroidEntity
import com.ampersand.data.service.remote.NasaApiService
import com.ampersand.data.utils.parseAsteroidsJsonResult
import com.ampersand.domain.model.AsteroidModel
import com.ampersand.domain.repository.NasaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class NasaRepositoryImpl @Inject constructor(
    private val nasaApiService: NasaApiService,
    private val dao: AsteroidDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : NasaRepository {

    override suspend fun fetchAsteroids() {
        try {
            val response = nasaApiService.fetchAsteroids()
            if (response.isSuccessful && response.body() != null) {
                val asteroidJson = JSONObject(response.body())
                val asteroidList = parseAsteroidsJsonResult(asteroidJson)

                asteroidList.forEach { asteroid ->
                    dao.insertAsteroid(asteroid)
                }
            }
        } catch (e: Exception) {
            Log.e("NasaRepositoryImpl", "fetchAsteroids: ${e.localizedMessage}")
        }
    }

    override fun getAsteroids(): Flow<List<AsteroidModel>> {
        return dao.fetchAsteroids()
            .catch { e: Throwable ->
                Log.e("NasaRepositoryImpl", "getAsteroids: ${e.localizedMessage}")
            }
            .map { value: List<AsteroidEntity> ->
                value.map {
                    it.toAsteroidModel()
                }
            }

    }


    override suspend fun getDayImage(): ApiResult<String> =
        withContext(ioDispatcher) {
            try {
                val response = nasaApiService.getDayImage()
                if (response.isSuccessful && response.body() != null) {
                    val url = JSONObject(response.body()).getString("url")
                    return@withContext ApiResult.Success(url)
                } else {
                    return@withContext ApiResult.Error(message = UiText.StringResource(R.string.error_not_found))
                }
            } catch (e: Exception) {
                return@withContext ApiResult.Error(message = UiText.DynamicString(e.localizedMessage))
            }
        }
}
