package com.ampersand.data.service.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ampersand.data.service.local.entitiy.AsteroidEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {

    @Upsert
    suspend fun insertAsteroid(asteroidEntity: AsteroidEntity)

    @Query("SELECT * FROM asteroid_entity")
    fun fetchAsteroids(): Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid_entity WHERE id = :id")
    suspend fun getAsteroidById(id: Long): AsteroidEntity?
}
