package com.ampersand.domain.use_case

import com.ampersand.core.ApiResult
import com.ampersand.domain.repository.NasaRepository

class GetDayImageUseCase(
    private val repository: NasaRepository
) {
    suspend operator fun invoke(): ApiResult<String> {
        return repository.getDayImage()
    }
}
