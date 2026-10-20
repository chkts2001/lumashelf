package com.gemminiii.core.ui.component.unused

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun DurationRow(
    label: String,
    duration: Int?
) {
    Column{
        Row() {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )
            Text(
                text =":",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1
            )
        }
        Text(
            text = "${makeHoursFromMinutes(duration)}ч. ${makeRemainderMinutes(duration)}м.",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            //modifier = Modifier.weight(1f, fill = false)
        )
    }
}

fun makeHoursFromMinutes(minutes: Int?): Int{
    return minutes!! / 60
}

fun makeRemainderMinutes(minutes: Int?): Int{
    return minutes!! % 60
}
