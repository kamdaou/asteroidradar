package com.ampersand.domain.use_case

data class UseCaseWrapper(
    val calculateCloseApproachUseCase: CalculateCloseApproachDaysUseCase,
    val fetchAsteroidsUseCase: FetchAsteroidsUseCase
)
