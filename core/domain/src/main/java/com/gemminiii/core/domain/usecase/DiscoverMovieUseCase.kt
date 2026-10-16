package com.gemminiii.core.domain.usecase

import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.domain.model.Movie
import com.gemminiii.core.domain.repository.MovieRepository
import javax.inject.Inject

class DiscoverMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(filters: DiscoverFilters): List<Movie> {
        return repository.discoverMovies(filters)
    }
}