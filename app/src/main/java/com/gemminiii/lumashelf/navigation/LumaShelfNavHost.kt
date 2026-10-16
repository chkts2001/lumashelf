package com.gemminiii.lumashelf.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gemminiii.feature.discover.navigation.DISCOVER_ROUTE
import com.gemminiii.feature.discover.navigation.discoverScreen

@Composable
fun LumaShelfNavHost(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DISCOVER_ROUTE
    ){
        discoverScreen (
            onMovieClick = {moveId ->
                navController.navigate("detail/$moveId")
            }
        )
    }
}