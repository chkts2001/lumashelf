package com.gemminiii.feature.discover.presentation.random

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.domain.model.Movie
import com.gemminiii.core.domain.usecase.GetRandomMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomMovieViewModel @Inject constructor(
    private val getRandomMovie: GetRandomMovieUseCase
): ViewModel(){
    private val _state = MutableStateFlow<RandomMovieUiState>(RandomMovieUiState.Idle)
    val state: StateFlow<RandomMovieUiState> = _state.asStateFlow()

    val countRandomMovies = 3
    val debugMovies = listOf(
        Movie(
            id = 1,
            title = "Вечное сияние чистого разума вечное сияние чистого разума",
            originalTitle = "The Shawshank Redemption",
            description = "История о надежде и дружбе в тюрьме.",
            posterUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
            ratingKp = 9.1,
            ratingImdb = 9.3,
            votesKp = 300_000,
            votesImdb = 2_800_000,
            year = 1994,
            genres = listOf("драма", "криминал"),
            durationMinutes = 142,
            countries = listOf("США", "Франция")
        ),
        Movie(
            id = 2,
            title = "Крёстный отец",
            originalTitle = "The Godfather",
            description = "Классика гангстерского кино.",
            posterUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_325.jpg",
            ratingKp = 9.0,
            ratingImdb = 9.2,
            votesKp = 250_000,
            votesImdb = 2_000_000,
            year = 1972,
            genres = listOf("драма", "криминал"),
            durationMinutes = 175,
            countries = listOf("США", "Россия")
        ))


    fun loadRandomMovie(){
        Log.i("random", "inside random loader")
        viewModelScope.launch {
            _state.value = RandomMovieUiState.Loading
            try{
                val movies = if(countRandomMovies == 0) debugMovies else getRandomMovie.invoke(countRandomMovies, DiscoverFilters(ratingFrom = 5.0, ratingTo = 10.0))
                if(movies.isEmpty()) {
                    _state.value = RandomMovieUiState.Error("Не удалось найти фильмы")
                    Log.e("random", "error loading random film: empty")
                }else{
                    _state.value = RandomMovieUiState.Success(movies)

                }
            }catch (e: Exception){
                Log.e("random", "error loading random film ${e.message.toString()}")
                _state.value = RandomMovieUiState.Error(e.message.toString() ?: "Ошибка загрузки")
            }
        }
    }
}