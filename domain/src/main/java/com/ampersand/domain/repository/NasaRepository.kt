package com.ampersand.domain.repository

interface NasaRepository {

    suspend fun fetchAsteroids(): String
}
