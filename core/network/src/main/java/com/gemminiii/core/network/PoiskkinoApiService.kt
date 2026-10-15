package com.gemminiii.core.network
import com.gemminiii.core.network.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface PoiskkinoApiService {
    @GET("v1.4/movie")
    suspend fun discoverMovies(
        @Query("genres.name") genre: String? = null,
        @Query("year") year: String? = null,
        @Query("rating") rating: String? = null,
        @Query("limit") linit: Int = 20
    ): MovieListResponse

    @GET("v1.4/movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int
    ): MovieDto
}

@kotlinx.serialization.Serializable
data class MovieListResponse(val docs: List<MovieDto>)