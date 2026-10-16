package com.gemminiii.feature.discover.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.domain.usecase.DiscoverMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val discoverMovies: DiscoverMovieUseCase
): ViewModel() {
    private val _state = MutableStateFlow<DiscoverUiState>(DiscoverUiState.Loading)
    val state: StateFlow<DiscoverUiState> = _state.asStateFlow()

    init{

    }

    fun loadMovies(filters: DiscoverFilters){
        viewModelScope.launch {
            _state.value = DiscoverUiState.Loading
            try{
                val movies = discoverMovies(filters)
                _state.value = if(movies.isEmpty()){
                    DiscoverUiState.Empty
                }else{
                    DiscoverUiState.Success(movies)
                }
            }catch (e: Exception){
                _state.value = DiscoverUiState.Error(e.message ?: "Не удалось загрузить фильмы")
            }
        }
    }
}