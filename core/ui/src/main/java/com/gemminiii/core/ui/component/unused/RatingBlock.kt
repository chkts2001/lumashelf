package com.gemminiii.core.ui.component.unused

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RatingBlock(
    ratingKp: Double?,
    votesKp: Int?,
    ratingImdb: Double?,
    votesImdb: Int?,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)){
        Log.d("search", "kpr2 = ${ratingKp}\nimdbr2 = ${ratingImdb}")
        if(ratingKp != null) {
            RatingRow(
                label = "⭐ kp:",
                rating = ratingKp,
                votes = votesKp,
            )
        }
        if(ratingImdb != null){
            RatingRow(
                label = "⭐ imdb:",
                rating = ratingImdb,
                votes = votesImdb
            )
        }
    }
}