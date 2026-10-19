package com.gemminiii.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun GenreList(genres: List<String>) {
    val displayGenres = genres.take(2)
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        displayGenres.forEach { genre ->
            Text(
                text = genre,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (genres.size > 2) {
            Text(
                text = "+${genres.size - 2}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}