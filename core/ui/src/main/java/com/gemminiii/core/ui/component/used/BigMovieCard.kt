package com.gemminiii.core.ui.component.used

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gemminiii.core.domain.model.Movie

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BigMovieCard(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var infoBlockHeightPx by remember { mutableStateOf(0f) }
    var cardHeightPx by remember { mutableStateOf(0f) }
    val density = LocalDensity.current
    val cardHeightDp = with(density) { cardHeightPx.toDp() }


        // Высота блока в свёрнутом состоянии (видимая часть)
        val collapsedHeight = 10.dp



        val collapsedHeightPx = infoBlockHeightPx
        val anchors = remember(cardHeightPx, infoBlockHeightPx) {
            if (cardHeightPx > 0f && infoBlockHeightPx > 0f) {
                DraggableAnchors {
                    0f at (cardHeightPx - infoBlockHeightPx)
                    1f at (cardHeightPx / 6f)
                }
            } else {
                DraggableAnchors { } // пустые, пока не измерено
            }
        }

        val state = remember(anchors) {
            AnchoredDraggableState(initialValue = 0f, anchors = DraggableAnchors {  })
        }

        LaunchedEffect(cardHeightPx, infoBlockHeightPx) {
            if (cardHeightPx > 0f && infoBlockHeightPx > 0f) {
                state.updateAnchors(
                    DraggableAnchors {
                        0f at (cardHeightPx - infoBlockHeightPx)
                        1f at (cardHeightPx / 6f)
                    }
                )
            }
        }

        val offsetY = with(density){
            if (state.offset.isNaN()) 0.dp else state.offset.toDp()
        }

        val flingBehavior = AnchoredDraggableDefaults.flingBehavior<Float>(
            state = state,
            positionalThreshold = { distance -> distance * 0.2f },
            animationSpec = tween(durationMillis = 600) // ← здесь задаём длительность
        )

        // Обновляем якоря при изменении высоты карточки
        LaunchedEffect(anchors) {
            state.updateAnchors(anchors)
        }

        Card(
            modifier = modifier
                .onSizeChanged { cardHeightPx = it.height.toFloat() }
                .clickable { onClick() },
            shape = RoundedCornerShape(0.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // 1. Постер (фон)
                Column {
                    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().aspectRatio(0.75f)) {
                            AsyncImage(
                                model = movie.posterUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize().blur(20.dp)
                            )
                        }
                        Column(modifier = Modifier.fillMaxSize()) {
                            Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)).padding(vertical = 10.dp, horizontal = 8.dp).fillMaxWidth()){
                                Text("Случайный фильм", style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp), textAlign = TextAlign.Start)
                            }
                            Box(
                                modifier = Modifier.padding(top = 10.dp).align(Alignment.CenterHorizontally)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(movie.posterUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = movie.title,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .fillMaxWidth(1f / 1.705f)
                                        .aspectRatio(2f / 3f)
                                        .clip(RoundedCornerShape(12.dp))
                                )
                            }
                        }
                    }
                }

                // 2. Блок с информацией — высотой во всю карточку, но сдвинут вниз в свёрнутом состоянии
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(cardHeightDp) // ← высота = высоте карточки
                        .offset(y = offsetY) // ← сдвиг
                        .anchoredDraggable<Float>(state = state, orientation = Orientation.Vertical, flingBehavior = flingBehavior)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
                            RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        )
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .onSizeChanged { size ->
                                    infoBlockHeightPx = size.height.toFloat()
                                }
                        ) {
                            BigCardShortInfoBlock(movie)
                        }
                        Box(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                            Text(
                                movie.description!!,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(6.dp)
                                    .fillMaxWidth().padding(bottom = 100.dp),
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                }
            }
        }
}