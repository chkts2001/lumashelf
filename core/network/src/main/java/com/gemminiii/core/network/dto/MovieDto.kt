package com.gemminiii.core.network.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val name: String? = null,
    @SerialName("alternativeName") val alternativeName: String? = null,
    val shortDescription: String? = null,
    val poster: PosterDto? = null,
    val rating: RatingDto? = null,
    val year: Int? = null,
    val genres: List<GenreDto>? = null,
    @SerialName("movieLength") val movieLength: Int? = null,
    val countries: List<CountryDto>? = null,
    val votes: VotesDto? = null
)

@Serializable
data class PosterDto(val url: String? = null)

@Serializable
data class RatingDto(val kp: Double? = null, val imdb: Double? = null)

@Serializable
data class GenreDto(val name: String? = null)

@Serializable
data class CountryDto(val name: String? = null, val id: Int? = null)

@Serializable
data class VotesDto(val kp: Int? = null, val imdb: Int? = null)
