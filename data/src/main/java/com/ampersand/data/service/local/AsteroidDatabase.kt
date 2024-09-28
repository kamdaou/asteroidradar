package com.ampersand.data.service.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ampersand.data.service.local.entitiy.AsteroidEntity

@Database(
    entities = [AsteroidEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val asteroidDao: AsteroidDao
}
