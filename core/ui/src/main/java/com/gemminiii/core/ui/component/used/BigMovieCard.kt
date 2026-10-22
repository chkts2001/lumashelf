package com.gemminiii.core.ui.component.used

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
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
    //val collapsedHeightPx = with(density) { collapsedHeight.toPx() }

    // Якоря:
    // 0f — свёрнуто: блок сдвинут вниз так, что виден только верх (collapsedHeight)
    // 1f — развёрнуто: блок сдвинут вверх до 0, перекрывает всю карточку
    val collapsedHeightPx = infoBlockHeightPx // ← измеренная высота
    val anchors = remember(cardHeightPx, collapsedHeightPx) {
        DraggableAnchors {
            0f at (cardHeightPx - collapsedHeightPx) // свёрнуто: виден только верх
            1f at 0f // развёрнуто: перекрывает всю карточку
        }
    }

    val state = remember(anchors) {
        AnchoredDraggableState(
            initialValue = 0f,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { with(density) { 0.9f } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay()
        )
    }

    // Обновляем якоря при изменении высоты карточки
    LaunchedEffect(anchors) {
        state.updateAnchors(anchors)
    }

    Card(
        modifier = modifier
            .onSizeChanged { cardHeightPx = it.height.toFloat() }
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 1. Постер (фон)
            Column {
                Box(modifier = Modifier.fillMaxWidth().aspectRatio(1.13f)) {
                    AsyncImage(
                        model = movie.posterUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().blur(20.dp)
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
                                .fillMaxWidth(1f / 1.7f)
                                .aspectRatio(2f / 3f)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }

            // 2. Блок с информацией — высотой во всю карточку, но сдвинут вниз в свёрнутом состоянии
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(cardHeightDp) // ← высота = высоте карточки
                    .offset(y = with(density) { state.offset.toDp() }) // ← сдвиг
                    .anchoredDraggable(state, Orientation.Vertical)
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                Box(
                    modifier = Modifier.onSizeChanged { size ->
                        infoBlockHeightPx = size.height.toFloat()
                    }
                ) {
                    BigCardShortInfoBlock(movie)
                }
            }
        }
    }
}