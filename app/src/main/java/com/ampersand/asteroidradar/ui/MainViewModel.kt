package com.ampersand.asteroidradar.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ampersand.core.ApiResult
import com.ampersand.domain.repository.NasaRepository
import com.ampersand.domain.use_case.UseCaseWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: NasaRepository,
    private val useCaseWrapper: UseCaseWrapper
) : ViewModel() {

    init {
        viewModelScope.launch {
            val result = repository.fetchAsteroids()
            if (result is ApiResult.Success)
                useCaseWrapper.calculateCloseApproachUseCase(result.data.first())
            Log.i("MainViewModel", "fetchedAsteroids: $result")
        }
    }
}
