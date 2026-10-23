package com.gemminiii.feature.discover.presentation.discover

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.domain.model.Movie
import com.gemminiii.core.domain.usecase.DiscoverMovieUseCase
import com.gemminiii.core.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val discoverMovies: DiscoverMovieUseCase,
    private val searchMovies: SearchMoviesUseCase
): ViewModel() {
    private val _state = MutableStateFlow<DiscoverUiState>(DiscoverUiState.Loading)
    val state: StateFlow<DiscoverUiState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    var debugFlag = true

    val debugMovies = listOf(
        Movie(
            id = 1,
            title = "Побег из Шоушенка",
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
            countries = listOf("США", "Франция"),
            slogan = "1"
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
            countries = listOf("США", "Россия"),
            slogan = "1"
        ),
        Movie(
            id = 3,
            title = "Тёмный рыцарь",
            originalTitle = "The Dark Knight",
            description = "Бэтмен против Джокера.",
            posterUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_111543.jpg",
            ratingKp = 8.9,
            ratingImdb = 9.0,
            votesKp = 280_000,
            votesImdb = 2_900_000,
            year = 2008,
            genres = listOf("боевик", "триллер"),
            durationMinutes = 152,
            countries = listOf("США", "Италия", "Вьетнам", "Китай", "Германия"),
            slogan = "1"
        ),
        Movie(
            id = 4,
            title = "Криминальное чтиво",
            originalTitle = "Pulp Fiction",
            description = "Несколько переплетающихся историй.",
            posterUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_342.jpg",
            ratingKp = 8.8,
            ratingImdb = 8.9,
            votesKp = 220_000,
            votesImdb = 2_200_000,
            year = 1994,
            genres = listOf("драма", "криминал"),
            durationMinutes = 154,
            countries = listOf("США"),
            slogan = "1"
        ),
        Movie(
            id = 5,
            title = "Форрест Гамп",
            originalTitle = "Forrest Gump",
            description = "История жизни простого человека.",
            posterUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_448.jpg",
            ratingKp = 8.8,
            ratingImdb = 8.8,
            votesKp = 260_000,
            votesImdb = 2_300_000,
            year = 1994,
            genres = listOf("драма", "романтика"),
            durationMinutes = 142,
            countries = listOf("США"),
            slogan = "1"
        )
    )

    init{
        loadMovies(DiscoverFilters(ratingFrom = 5.0, ratingTo = 10.0))
        observeQuery()
    }

    fun onQueryChange(newQuery: String){
        _query.value = newQuery
    }

    private fun observeQuery(){
        viewModelScope.launch {
            _query
                .debounce(700L)
                .distinctUntilChanged()
                .collect { q ->
                    if(q.isBlank()){
                        loadMovies(DiscoverFilters(ratingFrom = 5.0, ratingTo = 10.0))
                    }else{
                        searchMoviesVm(q)
                    }
                }
        }
    }

    fun loadMovies(filters: DiscoverFilters){
        viewModelScope.launch {
            _state.value = DiscoverUiState.Loading
            Log.i("discover", "status: ${_state.value}")
            try{
                val movies = if(debugFlag) debugMovies else discoverMovies(filters)
                Log.i("discover", "movies finded: $movies")
                _state.value = if(movies.isEmpty()){
                    DiscoverUiState.Empty
                }else{
                    DiscoverUiState.Success(movies)
                }
                Log.i("discover", "status: ${_state.value}")
            }catch (e: Exception){
                Log.e("discover", "error: ${e.message.toString()}")
                _state.value = DiscoverUiState.Error(e.message ?: "Не удалось загрузить фильмы")
            }
        }
    }

    private fun searchMoviesVm(query: String) {
        viewModelScope.launch {
            _state.value = DiscoverUiState.Loading
            try {
                val movies = searchMovies(query)
                _state.value = if (movies.isEmpty()) {
                    DiscoverUiState.Empty
                } else {
                    DiscoverUiState.Success(movies)
                }
            } catch (e: Exception) {
                _state.value = DiscoverUiState.Error(e.message ?: "Ошибка поиска")
            }
        }
    }
}