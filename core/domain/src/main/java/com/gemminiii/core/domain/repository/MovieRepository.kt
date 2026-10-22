package com.gemminiii.core.domain.repository

import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.domain.model.Movie

interface MovieRepository {
    suspend fun discoverMovies(filters: DiscoverFilters): List<Movie>
    suspend fun getMovieDetails(id: Int): Movie
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getRandomMovies(count: Int, filters: DiscoverFilters): List<Movie>
}