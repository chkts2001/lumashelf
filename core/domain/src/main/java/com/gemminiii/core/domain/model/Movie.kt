package com.gemminiii.core.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val description: String?,
    val posterUrl: String?,
    val rating: Double,
    val year: Int,
    val genres: List<String>,
    val durationMinutes: Int?,
    val countries: List<String>,
)