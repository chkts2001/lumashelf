package com.gemminiii.feature.discover.presentation.discover.main.random

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemminiii.core.ui.component.used.BigMovieCard

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
        shape = RoundedCornerShape(12.dp)
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
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.weight(1f),
                        pageSpacing = 16.dp
                    ) { page ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                BigMovieCard(
                                    movie = movies[page],
                                    onClick = { onMovieClick(movies[page].id) },
                                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                )
                            }
                        }
                    }

                    Row(Modifier.padding(vertical = 8.dp)) {
                        repeat(movies.size) { page ->
                            val selected = pagerState.currentPage == page
                            Box(
                                Modifier
                                    .padding(horizontal = 4.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (selected) MaterialTheme.colorScheme.onSurface
                                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                                    )
                                    .size(if (selected) 8.dp else 6.dp)
                            )
                        }
                    }
                }

                    //Button(onClick = onReload) { Text("Другой набор") }
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