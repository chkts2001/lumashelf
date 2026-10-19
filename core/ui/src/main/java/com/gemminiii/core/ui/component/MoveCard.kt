package com.gemminiii.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.gemminiii.core.domain.model.Movie
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) { // ← Column, не Row
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                alignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxWidth(2f / 3f)
                    .aspectRatio(2f / 3f) // ← сначала высота
                    .padding(top = 8.dp, start = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Green.copy(alpha = 0.2f))
            )
                Spacer(modifier = Modifier.width(8.dp))

                // 2. Список справа: страны, жанры, год
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // --- Страны (2 строки по 2 флага) ---
                    CountryFlagsGrid(countries = movie.countries)

                    // --- Жанры (2 строки по 1 жанру) ---
                    GenreList(genres = movie.genres)

                    // --- Год (1 строка) ---
                    Text(
                        text = "Год: ${movie.year ?: "—"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Column(modifier = Modifier.padding(8.dp)) { // ← текст под постером
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1
                )
                Text(
                    text = movie.genres.take(2).joinToString (separator =  " · " ) + if(movie.genres.size > 2) " · + ${movie.genres.size - 2}" else "",
                    style = MaterialTheme.typography.bodySmall
                )
//                Text(
//                    text = "${movie.year ?: "—"} · ${movie.rating}",
//                    style = MaterialTheme.typography.bodySmall
//                )
            }
        }
    }
}