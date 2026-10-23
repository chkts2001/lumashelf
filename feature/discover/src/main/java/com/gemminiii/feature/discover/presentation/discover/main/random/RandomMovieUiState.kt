package com.gemminiii.feature.discover.presentation.discover.main.random

import android.os.Message
import com.gemminiii.core.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel

sealed interface RandomMovieUiState {
    object Idle: RandomMovieUiState
    object Loading: RandomMovieUiState
    data class Success(val movie: List<Movie>): RandomMovieUiState
    data class Error(val message: String): RandomMovieUiState
}