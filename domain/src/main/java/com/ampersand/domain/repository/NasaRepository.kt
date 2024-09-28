package com.ampersand.domain.repository

import com.ampersand.core.ApiResult
import com.ampersand.domain.model.AsteroidModel

interface NasaRepository {

    suspend fun fetchAsteroids(): ApiResult<List<AsteroidModel>>
}
