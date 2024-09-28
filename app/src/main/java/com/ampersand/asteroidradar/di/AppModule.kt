package com.ampersand.asteroidradar.di

import com.ampersand.asteroidradar.ui.MainViewModel
import com.ampersand.domain.repository.NasaRepository
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
    fun provideViewModel(repository: NasaRepository): MainViewModel {
        return MainViewModel(repository)
    }
}
