package com.gemminiii.core.ui.component.used

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemminiii.core.domain.model.Movie

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BigCardShortInfoBlock(movie: Movie){
    BoxWithConstraints(modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Color.Green.copy(alpha = 0.2f))) {
        val parentWidth = maxWidth
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(parentWidth * 0.50f),
            )
            Spacer(modifier = Modifier.width(10.dp))
            ShortInfoValueRow(
                mode = ModeInfoValue.YEAR,
                value = movie.year,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start
            )
            ShortInfoValueRow(
                mode = ModeInfoValue.RATING,
                value = movie.ratingKp,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}