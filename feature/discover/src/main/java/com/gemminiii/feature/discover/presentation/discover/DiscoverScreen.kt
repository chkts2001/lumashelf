package com.gemminiii.feature.discover.presentation.discover

import com.gemminiii.feature.discover.presentation.main.MainContent
import com.gemminiii.feature.discover.presentation.search.SearchResultsGrid
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.ui.component.MovieCard
import com.gemminiii.feature.discover.presentation.random.RandomMovieBlock
import com.gemminiii.feature.discover.presentation.random.RandomMovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    randomViewModel: RandomMovieViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
){
    val state by viewModel.state.collectAsState()
    val query by viewModel.query.collectAsState()
    val randomState by randomViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        randomViewModel.loadRandomMovie()
    }

    Scaffold { innerPadding ->

        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            TextField(
                value = query,
                onValueChange = viewModel::onQueryChange,
                placeholder = { Text("Поищем?") },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.Green.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            )
            if(query.isBlank()) {
                MainContent(
                    state = state,
                    randomState = randomState,
                    onMovieClick = onMovieClick,
                    onReloadRandom = { randomViewModel.loadRandomMovie() },
                    onRetry = { viewModel.loadMovies(DiscoverFilters()) }
                )
            }else{
                SearchResultsGrid(
                    state = state,
                    onMovieClick = onMovieClick,
                    onRetry = { viewModel.onQueryChange(query) } // повторить поиск
                )
            }
        }
    }
}