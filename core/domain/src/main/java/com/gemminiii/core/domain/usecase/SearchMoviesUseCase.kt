package com.gemminiii.core.domain.usecase

import com.gemminiii.core.domain.model.Movie
import com.gemminiii.core.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
){
    suspend operator fun invoke(query: String): List<Movie>{
        return repository.searchMovies(query)
    }
}