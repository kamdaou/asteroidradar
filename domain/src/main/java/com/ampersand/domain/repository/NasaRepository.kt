package com.ampersand.domain.repository

import com.ampersand.core.ApiResult
import com.ampersand.domain.model.AsteroidModel
import kotlinx.coroutines.flow.Flow

interface NasaRepository {

    suspend fun fetchAsteroids()

    suspend fun getAsteroidById(id: Long): ApiResult<AsteroidModel>

    fun getAsteroids(): Flow<List<AsteroidModel>>
}
