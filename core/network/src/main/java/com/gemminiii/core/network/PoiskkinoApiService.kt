package com.gemminiii.core.network
import com.gemminiii.core.network.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PoiskkinoApiService {
    @GET("v1.5/movie")
    suspend fun discoverMovies(
        @Query("genres.name") genre: String? = null,
        @Query("year") year: String? = null,
        @Query("rating.kp") rating: String? = null,
        @Query("limit") limit: Int = 5
    ): MovieListResponse

    @GET("v1.5/movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int
    ): MovieDto

    @GET("v1.5/movie/search")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("limit") limit: Int = 20
    ): MovieListResponse

    @GET("v1.5/movie/random")
    suspend fun getRandomMovie(
        @Query("rating.kp") rating: String? = null
    ): MovieDto
}

@kotlinx.serialization.Serializable
data class MovieListResponse(val docs: List<MovieDto>)