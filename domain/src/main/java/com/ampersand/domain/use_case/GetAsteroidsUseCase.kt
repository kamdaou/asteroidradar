package com.ampersand.domain.use_case

import com.ampersand.domain.repository.NasaRepository
import javax.inject.Inject

class GetAsteroidsUseCase @Inject constructor(
    private val repository: NasaRepository
) {

    operator fun invoke() = repository.getAsteroids()
}
