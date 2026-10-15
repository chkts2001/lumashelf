package com.gemminiii.core.network.repository

import com.gemminiii.core.domain.model.Movie
import com.gemminiii.core.domain.model.DiscoverFilters
import com.gemminiii.core.domain.repository.MovieRepository
import com.gemminiii.core.network.PoiskkinoApiService
import com.gemminiii.core.network.dto.MovieDto
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: PoiskkinoApiService
): MovieRepository {
    override suspend fun discoverMovies(filters: DiscoverFilters): List<Movie>{
        val response = api.discoverMovies(
            genre = filters.genre,
            rating = filters.ratingFrom?.let {"$it-10"},
        )
        return response.docs.map { it.toDomain() }
    }

    override suspend fun getMovieDetails(id: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        TODO("Not yet implemented")
    }
}
private fun MovieDto.toDomain() = Movie(
    id = id,
    title = name ?: alternativeName ?: "Без названия",
    originalTitle = alternativeName ?: name ?: "",
    description = description ?: "Описание остутствует",
    posterUrl = poster?.url,
    rating = rating?.kp ?: 0.0,
    year = year ?: 0,
    genres = genres?.mapNotNull { it.name } ?: emptyList(),
    durationMinutes = movieLength
)