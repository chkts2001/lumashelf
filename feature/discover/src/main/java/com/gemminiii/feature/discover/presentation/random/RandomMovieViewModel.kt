package com.gemminiii.feature.discover.presentation.random

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    fun loadRandomMovie(){
        Log.i("random", "inside random loader")
        viewModelScope.launch {
            _state.value = RandomMovieUiState.Loading
            try{
                val movies = getRandomMovie(0)
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