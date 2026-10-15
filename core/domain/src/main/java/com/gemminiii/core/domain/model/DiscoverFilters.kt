package com.gemminiii.core.domain.model

data class DiscoverFilters(
    val genre: String? = null,
    val yearFrom: Int? = null,
    val yearTo: Int? = null,
    val ratingFrom: Double? = null,
    val ratingTo: Double? = null
)