package com.gemminiii.feature.discover.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gemminiii.core.ui.component.MovieCard
import com.gemminiii.feature.discover.presentation.discover.DiscoverUiState

@Composable
fun SearchResultsGrid(
    state: DiscoverUiState,
    onMovieClick: (Int) -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        when (state) {
            is DiscoverUiState.Start -> {}
            is DiscoverUiState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            is DiscoverUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // ← два столбца
                    contentPadding = PaddingValues(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items = state.movies, key = { it.id }) { movie ->
                        MovieCard(
                            movie = movie,
                            onClick = { onMovieClick(movie.id) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            is DiscoverUiState.Empty -> {
                Text(
                    text = "По запросу ничего не найдено",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is DiscoverUiState.Error -> {
                Column(
                    Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(state.message)
                    Button(onClick = onRetry) { Text("Повторить") }
                }
            }
        }
    }
}