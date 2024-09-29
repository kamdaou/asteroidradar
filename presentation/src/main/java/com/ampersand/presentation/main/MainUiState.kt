package com.ampersand.presentation.main

import com.ampersand.core.UiText
import com.ampersand.presentation.Asteroid

data class MainUiState(
    val loading: Boolean = false,
    val navigateToAsteroidDetail: Asteroid? = null,
    val selectedAsteroid: Asteroid? = null,
    val dayImageUrl: String = "",
    val error: UiText? = null,
)
