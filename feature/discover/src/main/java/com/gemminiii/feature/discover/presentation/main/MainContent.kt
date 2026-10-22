package com.gemminiii.feature.discover.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gemminiii.core.ui.component.used.LittleMovieCard
import com.gemminiii.feature.discover.presentation.discover.DiscoverUiState
import com.gemminiii.feature.discover.presentation.random.RandomMovieBlock
import com.gemminiii.feature.discover.presentation.random.RandomMovieUiState

@Composable
fun MainContent(
    state: DiscoverUiState,
    randomState: RandomMovieUiState,
    onMovieClick: (Int) -> Unit,
    onReloadRandom: () -> Unit,
    onRetry: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Горизонтальная лента
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // фиксированная высота для горизонтального списка
                .background(Color.Red.copy(alpha = 0.2f))
        ) {
            when (val currentState = state) {
                is DiscoverUiState.Start -> {}
                is DiscoverUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is DiscoverUiState.Success -> {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = currentState.movies,
                            key = { it.id }
                        ) { movie ->
                            LittleMovieCard(
                                movie = movie,
                                onClick = { onMovieClick(movie.id) },
                                modifier = Modifier.width(163.dp).fillMaxHeight(1f) // фиксированная ширина карточки
                            )
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
                        Text(currentState.message)
                        Button(onClick = onRetry) {
                            Text("Повторить")
                        }
                    }
                }
            }
        }

        RandomMovieBlock(
            state = randomState,
            onReload = onReloadRandom,
            onMovieClick = onMovieClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(Color.Blue.copy(alpha = 0.2f))
        )
    }
}