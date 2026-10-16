package com.gemminiii.feature.discover.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.ui.component.MovieCard

@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
){
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()){
        when(val currentState = state){
            is DiscoverUiState.Start -> {
                Button(onClick = { viewModel.loadMovies(DiscoverFilters()) }) {
                    Text("Поиск")
                }
            }
            is DiscoverUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is DiscoverUiState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(currentState.movies, key = {it.id}) {movie ->
                        MovieCard(movie = movie, onClick = {onMovieClick(movie.id)})
                    }
                }
            }
            is DiscoverUiState.Empty -> {
                Text(
                    text = "Ничего не найдено",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is DiscoverUiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = currentState.message)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadMovies(DiscoverFilters()) }) {
                        Text("Повторить")
                    }
                }
            }
        }
    }

}