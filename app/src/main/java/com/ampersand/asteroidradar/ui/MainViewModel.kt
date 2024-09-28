package com.ampersand.asteroidradar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ampersand.domain.model.AsteroidModel
import com.ampersand.domain.repository.NasaRepository
import com.ampersand.domain.use_case.UseCaseWrapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: NasaRepository,
    useCaseWrapper: UseCaseWrapper
) : ViewModel() {

    val asteroids = useCaseWrapper
        .getAsteroidsUseCase()
        .map { asteroids ->
            asteroids.map { asteroidModel: AsteroidModel ->
                val closeApproachDate = useCaseWrapper.calculateCloseApproachUseCase(asteroidModel)
                Asteroid(
                    id = asteroidModel.id,
                    codename = asteroidModel.codename,
                    closeApproachDate = closeApproachDate,
                    absoluteMagnitude = asteroidModel.absoluteMagnitude,
                    estimatedDiameter = asteroidModel.estimatedDiameter,
                    relativeVelocity = asteroidModel.relativeVelocity,
                    distanceFromEarth = asteroidModel.distanceFromEarth,
                    isPotentiallyHazardous = asteroidModel.isPotentiallyHazardous
                )
            }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.fetchAsteroids()
        }
    }
}
