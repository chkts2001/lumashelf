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
        val response = api.searchMovies(query)
        return response.docs.map { it.toDomain() }
    }

    override suspend fun getRandomMovies(count: Int, filters: DiscoverFilters): List<Movie> {
        val movies = mutableListOf<Movie>()
        repeat(count){
            try{
                val movie = api.getRandomMovie(
                    rating = filters.ratingFrom?.let {"$it-10"}
                ).toDomain()
                if(movies.none {it.id == movie.id}){
                    movies.add(movie)
                }
            }catch(e: Exception){

            }
        }
        return movies
    }
}
private fun MovieDto.toDomain() = Movie(
    id = id,
    title = name ?: alternativeName ?: "Без названия",
    originalTitle = alternativeName ?: name ?: "",
    description = shortDescription ?: "Описание остутствует",
    posterUrl = poster?.url,
    ratingKp = rating?.kp ?: 0.0,
    ratingImdb = rating?.imdb ?: 0.0,
    year = year ?: 0,
    genres = genres?.mapNotNull { it.name } ?: emptyList(),
    durationMinutes = movieLength,
    countries = countries?.mapNotNull { it.name } ?: emptyList(),
    votesKp = votes?.kp ?: 0,
    votesImdb = votes?.imdb ?: 0
)