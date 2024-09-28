package com.ampersand.data.di

import android.content.Context
import androidx.room.Room
import com.ampersand.data.repository.NasaRepositoryImpl
import com.ampersand.data.service.local.AsteroidDao
import com.ampersand.data.service.local.AsteroidDatabase
import com.ampersand.data.service.remote.NasaApiService
import com.ampersand.domain.repository.NasaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideNasaRepository(
        apiService: NasaApiService,
        asteroidDao: AsteroidDao
    ): NasaRepository {
        return NasaRepositoryImpl(
            nasaApiService = apiService,
            ioDispatcher = Dispatchers.IO,
            dao = asteroidDao
        )
    }

    @Provides
    @Singleton
    fun provideNasaApiService(): NasaApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(NasaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AsteroidDatabase {
        return Room
            .databaseBuilder(
                context,
                AsteroidDatabase::class.java,
                "asteroid_database"
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideAsteroidDao(
        database: AsteroidDatabase
    ): AsteroidDao {
        return database.asteroidDao
    }
}
