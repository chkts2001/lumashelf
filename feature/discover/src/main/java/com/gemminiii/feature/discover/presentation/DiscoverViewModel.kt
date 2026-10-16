package com.gemminiii.feature.discover.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.domain.repository.MovieRepository
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
import com.gemminiii.core.network.repository.MovieRepositoryImpl

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val discoverMovies: DiscoverMovieUseCase,
    private val searchMovies: SearchMoviesUseCase
): ViewModel() {
    private val _state = MutableStateFlow<DiscoverUiState>(DiscoverUiState.Loading)
    val state: StateFlow<DiscoverUiState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    init{
        loadMovies(DiscoverFilters())
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
                        loadMovies(DiscoverFilters())
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
                val movies = discoverMovies(filters)
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