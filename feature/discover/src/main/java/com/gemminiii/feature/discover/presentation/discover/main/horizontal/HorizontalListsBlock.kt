package com.gemminiii.feature.discover.presentation.discover.main.horizontal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemminiii.core.domain.model.Movie
import com.gemminiii.core.ui.component.used.LittleMovieCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalListsBlock(
    modifier: Modifier = Modifier,
    movies: Map<HorizontalListsMode, List<Movie>>,
    onMovieClick: (Int) -> Unit
) {
    // 1. Превращаем ключи Map в список, чтобы знать порядок страниц
    val modes = remember(movies) { listOf(HorizontalListsMode.SAVED, HorizontalListsMode.LIKED) }

    // 2. pageCount = количество списков в Map
    val pagerState = rememberPagerState(pageCount = { modes.size })

    Surface(
        modifier = modifier,
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        if (modes.isEmpty()) {
            // Если Map пустая — показываем заглушку (опционально)
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Пока ничего нет")
            }
        } else {
            Row() {
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    // 3. Берём текущий режим и список фильмов для него
                    val listMode = modes[page]
                    val movieList = movies[listMode].orEmpty()

                    Column(modifier = Modifier.fillMaxSize()) {
                        // 4. Заголовок зависит от режима
                        Text(
                            text = when (listMode) {
                                HorizontalListsMode.LIKED -> "Вы уже это видели?"
                                HorizontalListsMode.SAVED -> "В планах на вечер"
                            },
                            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 14.sp),
                            modifier = Modifier
                                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            textAlign = TextAlign.Start
                        )

                        LazyRow(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = movieList,
                                key = { it.id }
                            ) { movie ->
                                LittleMovieCard(
                                    movie = movie,
                                    onClick = { onMovieClick(movie.id) },
                                    modifier = Modifier
                                        .width(163.dp)
                                        .fillMaxHeight(1f)
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier.padding(top = 3.dp, end = 8.dp).wrapContentWidth().fillMaxHeight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    repeat(movies.size) { page ->
                        val selected = pagerState.currentPage == page
                        Box(
                            Modifier
                                .padding(vertical = 4.dp) // ← отступ между точками по вертикали
                                .clip(CircleShape)
                                .background(
                                    if (selected) MaterialTheme.colorScheme.onSurface
                                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                                )
                                .size(if (selected) 8.dp else 6.dp)
                        )
                    }
                }
            }
        }
    }
}

enum class HorizontalListsMode{
    SAVED, LIKED
}

