package com.ampersand.domain.di

import com.ampersand.domain.repository.NasaRepository
import com.ampersand.domain.use_case.CalculateCloseApproachDaysUseCase
import com.ampersand.domain.use_case.FetchAsteroidsUseCase
import com.ampersand.domain.use_case.UseCaseWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideUseCaseWrapper(
        calculateCloseApproachUseCase: CalculateCloseApproachDaysUseCase,
        fetchAsteroidsUseCase: FetchAsteroidsUseCase
    ): UseCaseWrapper {
        return UseCaseWrapper(calculateCloseApproachUseCase, fetchAsteroidsUseCase)
    }

    @Provides
    @Singleton
    fun provideCalculateCloseApproach(): CalculateCloseApproachDaysUseCase {
        return CalculateCloseApproachDaysUseCase()
    }

    @Provides
    @Singleton
    fun provideFetchAsteroids(repository: NasaRepository): FetchAsteroidsUseCase {
        return FetchAsteroidsUseCase(repository)
    }
}
