package com.ampersand.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ampersand.core.ApiResult
import com.ampersand.domain.model.AsteroidModel
import com.ampersand.domain.use_case.UseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCaseWrapper: UseCaseWrapper
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private var _navigateToAsteroidDetail = MutableStateFlow<Asteroid?>(null)
    val navigateToAsteroidDetail: StateFlow<Asteroid?> = _navigateToAsteroidDetail.asStateFlow()

    private val _selectedAsteroid = MutableStateFlow<Asteroid?>(null)
    val selectedAsteroid: StateFlow<Asteroid?> = _selectedAsteroid.asStateFlow()

    private val isTablet = MutableStateFlow(false)

    val asteroids = useCaseWrapper
        .getAsteroidsUseCase()
        .onStart {
            _loading.update {
                true
            }
        }
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
        .onEach { emitted ->
            emitted.firstOrNull()?.let { emitted ->
                _selectedAsteroid.update {
                    emitted
                }
            }
            if (_loading.value)
                _loading.update {
                    false
                }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private var _dayImageUrl = MutableStateFlow("")
    val dayImageUrl: StateFlow<String> = _dayImageUrl.asStateFlow()

    init {
        viewModelScope.launch {
            launch { useCaseWrapper.fetchAsteroidsUseCase() }
            val dayImageResponse = async { useCaseWrapper.getDayImageUseCase() }
            val awaited = dayImageResponse.await()
            if (awaited is ApiResult.Success) {
                _dayImageUrl.value = awaited.data
            }
        }
    }

    fun onAsteroidClicked(asteroidId: Long, isTablet: Boolean) {
        _selectedAsteroid.update {
            asteroids.value.find {
                it.id == asteroidId
            }
        }
        if (!isTablet) {
            _navigateToAsteroidDetail.update {
                _selectedAsteroid.value
            }
        }
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }

    fun isUsingTable(boolean: Boolean) {
        isTablet.update { boolean }
    }
}
