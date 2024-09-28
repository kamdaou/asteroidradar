package com.ampersand.domain.use_case

import com.ampersand.core.ApiResult
import com.ampersand.domain.model.AsteroidModel
import com.ampersand.domain.repository.NasaRepository

class FetchAsteroidsUseCase(
    private val repository: NasaRepository
) {
    suspend operator fun invoke(): ApiResult<List<AsteroidModel>> {
        return repository.fetchAsteroids()
    }
}
