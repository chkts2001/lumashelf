package com.gemminiii.core.ui.component.used

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gemminiii.core.domain.model.Movie

@Composable
fun LittleCardShortInfoBlock(movie: Movie) {
    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        ShortInfoValueRow(mode = ModeInfoValue.RATING, value = movie.ratingKp, horizontalArrangement = Arrangement.Start)
        ShortInfoValueRow(mode = ModeInfoValue.YEAR, value = movie.year, modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center)
        ShortInfoValueRow(mode = ModeInfoValue.VOTES, value = movie.votesKp, horizontalArrangement = Arrangement.End)
    }
}