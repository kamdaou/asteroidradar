package com.ampersand.data.di

import com.ampersand.data.repository.NasaRepositoryImpl
import com.ampersand.data.service.NasaApiService
import com.ampersand.domain.repository.NasaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideNasaRepository(
        apiService: NasaApiService
    ): NasaRepository {
        return NasaRepositoryImpl(
            nasaApiService = apiService,
            ioDispatcher = Dispatchers.IO
        )
    }

    @Provides
    @Singleton
    fun provideNasaApiService(): NasaApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NasaApiService::class.java)
    }
}
