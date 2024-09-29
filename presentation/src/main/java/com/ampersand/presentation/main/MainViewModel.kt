package com.ampersand.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ampersand.core.ApiResult
import com.ampersand.domain.model.AsteroidModel
import com.ampersand.domain.use_case.UseCaseWrapper
import com.ampersand.presentation.Asteroid
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    val asteroids = useCaseWrapper
        .getAsteroidsUseCase()
        .onStart {
            _uiState.update {
                it.copy(
                    loading = true
                )
            }
        }
        .map { asteroids ->
            asteroids.map { asteroidModel: AsteroidModel ->
                asteroidModel.toAsteroid()
            }
        }
        .onEach { emitted ->
            emitted.firstOrNull()?.let { firstEmittedAsteroid ->
                _uiState.update {
                    it.copy(
                        selectedAsteroid = firstEmittedAsteroid
                    )
                }
            }
            if (_uiState.value.loading)
                _uiState.update {
                    it.copy(
                        loading = false
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
            val dayImageResponse = useCaseWrapper.getDayImageUseCase()
            if (dayImageResponse is ApiResult.Success) {
                _uiState.update {
                    it.copy(
                        dayImageUrl = dayImageResponse.data
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        error = (dayImageResponse as ApiResult.Error).message
                    )
                }
            }
        }
    }

    fun onAsteroidClicked(asteroidId: Long, isTablet: Boolean) {
        _uiState.update { oldUiState ->
            oldUiState.copy(
                selectedAsteroid = asteroids.value.find {
                    it.id == asteroidId
                }
            )
        }

        if (!isTablet) {
            _uiState.update { oldUiState ->
                oldUiState.copy(
                    navigateToAsteroidDetail = uiState.value.selectedAsteroid
                )
            }
        }
    }

    fun onAsteroidDetailNavigated() {
        _uiState.update {
            it.copy(
                navigateToAsteroidDetail = null
            )
        }
    }

    fun onErrorShown() {
        _uiState.update {
            it.copy(
                error = null
            )
        }
    }

    private fun AsteroidModel.toAsteroid(): Asteroid {
        val closeApproachDate = useCaseWrapper.calculateCloseApproachUseCase(this)
        return Asteroid(
            id = id,
            codename = codename,
            closeApproachDate = closeApproachDate,
            absoluteMagnitude = absoluteMagnitude,
            estimatedDiameter = estimatedDiameter,
            relativeVelocity = relativeVelocity,
            distanceFromEarth = distanceFromEarth,
            isPotentiallyHazardous = isPotentiallyHazardous
        )
    }
}
