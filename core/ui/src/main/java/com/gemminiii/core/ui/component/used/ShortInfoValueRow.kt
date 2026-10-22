package com.gemminiii.core.ui.component.used

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gemminiii.core.ui.R

@Composable
fun ShortInfoValueRow(mode: ModeInfoValue, value: Number?, textStyle: TextStyle = MaterialTheme.typography.bodySmall, modifier: Modifier = Modifier, horizontalArrangement: Arrangement.Horizontal = Arrangement.Center) {
    Row(
        modifier= modifier.wrapContentHeight(),
        horizontalArrangement = horizontalArrangement
    ){
        if(mode != ModeInfoValue.YEAR) {
            Image(
                painter = painterResource(id = if(mode == ModeInfoValue.RATING) R.drawable.start_vect else R.drawable.user_vect),
                contentDescription = null,
                colorFilter = ColorFilter.tint(LocalContentColor.current),
                modifier = Modifier.size(12.dp).align(Alignment.CenterVertically)
            )
        }
        Spacer(Modifier.width(3.dp))
        Text(
            text = when(value){
                null -> "-"
                is Int -> if(mode == ModeInfoValue.VOTES) formatVotes(value) else value.toString()
                is Double -> "%.1f".format(value)
                else -> value.toString()
            },//.take(2).joinToString(separator = " · "),
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

fun formatVotes(votes: Int?): String {
    if (votes == null || votes == 0) return "—"
    return when {
        votes >= 1_000_000 -> "${votes / 1_000_000}М"
        votes >= 1_000 -> "${votes / 1_000}К"
        else -> votes.toString()
    }
}

enum class ModeInfoValue{
    RATING, YEAR, VOTES
}