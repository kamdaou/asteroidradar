package com.ampersand.domain.use_case

data class UseCaseWrapper(
    val calculateCloseApproachUseCase: CalculateCloseApproachDaysUseCase,
    val fetchAsteroidsUseCase: FetchAsteroidsUseCase,
    val getAsteroidsUseCase: GetAsteroidsUseCase,
    val getDayImageUseCase: GetDayImageUseCase
)
