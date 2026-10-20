package com.gemminiii.core.ui.component.unused

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun RatingRow(
    label: String,
    rating: Double,
    votes: Int?
) {
    Column{
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            //modifier = Modifier.weight(1f, fill = false)
        )
        Text(
            text = if(rating == 0.0) " - / - " else "${"%.1f".format(rating)}/${formatVotes(votes)}",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = ratingColor(rating),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            //modifier = Modifier.weight(1f, fill = false)
        )
    }
}

private fun ratingColor(rating: Double): Color {
    return when {
        rating >= 7.0 -> Color(0xFF4CAF50)
        rating >= 5.0 -> Color(0xFFFFC107)
        else -> Color(0xFFF44336)
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