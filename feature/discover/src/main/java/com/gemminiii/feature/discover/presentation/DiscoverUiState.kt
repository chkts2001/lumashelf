package com.gemminiii.feature.discover.presentation
import com.gemminiii.core.domain.model.Movie

sealed interface DiscoverUiState {
    object Loading: DiscoverUiState
    data class Success(val movies: List<Movie>): DiscoverUiState
    data class Error(val message: String): DiscoverUiState
    object Empty : DiscoverUiState
}