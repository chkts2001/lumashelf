package com.gemminiii.core.domain.usecase

import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.domain.model.Movie
import com.gemminiii.core.domain.repository.MovieRepository

class DiscoverMovieUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(filters: DiscoverFilters): List<Movie> {
        return repository.discoverMovies(filters)
    }
}