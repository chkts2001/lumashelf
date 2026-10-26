package com.gemminiii.feature.discover.presentation.discover

import com.gemminiii.feature.discover.presentation.discover.main.MainContent
import com.gemminiii.feature.discover.presentation.discover.search.SearchResultsGrid
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.core.R
import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.ui.component.used.SearchField
import com.gemminiii.feature.discover.presentation.discover.main.random.RandomMovieViewModel

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
            val interactionSource = remember { MutableInteractionSource() }
            SearchField(
                query = query,
                onValueChange = viewModel::onQueryChange,
                interactionSource = interactionSource
            )
            if(query.isBlank()) {
                MainContent(
                    state = state,
                    randomState = randomState,
                    onMovieClick = onMovieClick,
                    onReloadRandom = { randomViewModel.loadRandomMovie() },
                    onRetry = { viewModel.loadMovies(DiscoverFilters(ratingFrom = 5.0, ratingTo = 10.0)) }
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