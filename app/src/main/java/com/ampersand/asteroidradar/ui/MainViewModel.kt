package com.ampersand.asteroidradar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ampersand.domain.repository.NasaRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: NasaRepository
): ViewModel() {

    init {
        viewModelScope.launch {
            repository.fetchAsteroids()
        }
    }
}
