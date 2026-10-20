package com.gemminiii.core.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.gemminiii.core.domain.model.Movie
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(1f)) { // ← Column, не Row
            Box(modifier = Modifier.wrapContentHeight()) {
                // 1. Размытый фон (та же картинка)
                AsyncImage(
                    model = movie.posterUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Растягиваем, чтобы заполнить края
                    modifier = Modifier

                        .aspectRatio(3f / 3f)
                        .blur(20.dp) // ← Сила размытия
                )
                Box(modifier = Modifier.padding(4.dp).align(Alignment.TopCenter)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movie.posterUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = movie.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth(2f / 3f)
                            .aspectRatio(2f / 3f) // ← сначала высота
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Green.copy(alpha = 0.2f))
                    )
                }
            }
            Column(modifier = Modifier.padding(top = 6.dp, start = 8.dp, end = 8.dp, bottom = 8.dp).wrapContentHeight()) { // ← текст под постером
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    //modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${movie.genres[0]}",//.take(2).joinToString(separator = " · "),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "⭐${movie.ratingKp}  ${movie.year}",//.take(2).joinToString(separator = " · "),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
//                    if (movie.genres.size > 2) {
//                        Text(
//                            text = " + ${movie.genres.size - 2}",
//                            style = MaterialTheme.typography.bodySmall,
//                            maxLines = 1
//                        )
//                    }
//                }
            }
        }
    }
}

                    // 2. Список справа: страны, жанры, год
//                Column(
//                    modifier = Modifier.weight(1f).padding(start = 4.dp, end = 4.dp),
//                    verticalArrangement = Arrangement.spacedBy(6.dp)
//                ) {
//                    DurationRow("Длина", movie.durationMinutes)
//                    // --- Страны (2 строки по 2 флага) ---
//                    CountryFlagsGrid(countries = movie.countries)
//                    Log.w("search", "kpr = ${movie.ratingKp}\nkpv = ${movie.votesKp}\nimdbr = ${movie.ratingImdb}\nimdbv = ${movie.votesImdb}")
//                    RatingBlock(
//                        ratingKp = movie.ratingKp,
//                        votesKp = movie.votesKp,
//                        ratingImdb = movie.ratingImdb,
//                        votesImdb = movie.votesImdb
//                    )
//
//                    // --- Жанры (2 строки по 1 жанру) ---
//                    //GenreList(genres = movie.genres)
//
//                    // --- Год (1 строка) ---
//                    Text(
//                        text = "Год:\n${movie.year ?: "—"}",
//                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                }
//                }
//                Column(modifier = Modifier.padding(8.dp)) { // ← текст под постером
//                    Text(
//                        text = movie.title,
//                        style = MaterialTheme.typography.titleSmall,
//                        maxLines = 1
//                    )
//                    Row(modifier = Modifier.fillMaxWidth()) {
//                        Text(
//                            text = movie.genres.take(2).joinToString(separator = " · "),
//                            style = MaterialTheme.typography.bodySmall,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            modifier = Modifier.weight(1f, fill = false)
//                        )
//                        if (movie.genres.size > 2) {
//                            Text(
//                                text = " + ${movie.genres.size - 2}",
//                                style = MaterialTheme.typography.bodySmall,
//                                maxLines = 1
//                            )
//                        }
//            }
//        }
//    }
//}