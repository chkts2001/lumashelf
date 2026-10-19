package com.gemminiii.feature.discover.presentation.random

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gemminiii.core.ui.component.MovieCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RandomMovieBlock(
    state: RandomMovieUiState,
    onReload: () -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(8.dp),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        when (state) {
            is RandomMovieUiState.Idle -> {}
            is RandomMovieUiState.Loading -> {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is RandomMovieUiState.Success -> {
                val movies = state.movie
                val pagerState = rememberPagerState(pageCount = { movies.size })

                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Рекомендация на вечер", style = MaterialTheme.typography.titleMedium)

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.weight(1f),
                        pageSpacing = 16.dp
                    ) { page ->
                        Box(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            MovieCard(
                                movie = movies[page],
                                onClick = { onMovieClick(movies[page].id) }
                            )
                        }
                    }

                    Row(Modifier.padding(vertical = 8.dp)) {
                        repeat(movies.size) { i ->
                            val selected = pagerState.currentPage == i
                            Box(
                                Modifier
                                    .padding(horizontal = 4.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (selected) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                                    )
                                    .size(if (selected) 12.dp else 8.dp)
                            )
                        }
                    }

                    Button(onClick = onReload) { Text("Другой набор") }
                }
            }
            is RandomMovieUiState.Error -> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(state.message)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = onReload) { Text("Повторить") }
                }
            }
        }
    }
}