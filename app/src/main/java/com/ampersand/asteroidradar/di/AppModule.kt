package com.ampersand.asteroidradar.di

import com.ampersand.asteroidradar.ui.MainViewModel
import com.ampersand.domain.repository.NasaRepository
import com.ampersand.domain.use_case.UseCaseWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideViewModel(
        repository: NasaRepository,
        useCaseWrapper: UseCaseWrapper
    ): MainViewModel {
        return MainViewModel(repository, useCaseWrapper)
    }
}
