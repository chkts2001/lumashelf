package com.gemminiii.feature.discover.navigation

import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gemminiii.feature.discover.presentation.DiscoverScreen

const val DISCOVER_ROUTE = "discover"

fun NavGraphBuilder.discoverScreen(
    onMovieClick: (Int) -> Unit
){
    composable(DISCOVER_ROUTE){
        DiscoverScreen(onMovieClick = onMovieClick)
    }
}