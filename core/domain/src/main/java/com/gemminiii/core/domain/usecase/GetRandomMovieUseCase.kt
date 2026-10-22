package com.gemminiii.core.domain.usecase

import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.domain.model.Movie
import com.gemminiii.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetRandomMovieUseCase @Inject constructor(
    private val repository: MovieRepository
){
    suspend operator fun invoke(count: Int = 5, filters: DiscoverFilters): List<Movie>{
        return repository.getRandomMovies(count, filters)
    }
}