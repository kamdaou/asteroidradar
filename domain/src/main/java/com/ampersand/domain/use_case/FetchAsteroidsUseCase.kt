package com.ampersand.domain.use_case

import com.ampersand.domain.repository.NasaRepository
import javax.inject.Inject

class FetchAsteroidsUseCase @Inject constructor(
    private val repository: NasaRepository
) {
    suspend operator fun invoke() {
        repository.fetchAsteroids()
    }
}
