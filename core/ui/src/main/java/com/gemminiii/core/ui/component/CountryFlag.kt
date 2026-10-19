package com.gemminiii.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CountryFlagsGrid(countries: List<String>) {
    val displayCountries = countries.take(4)
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        displayCountries.chunked(2).forEach { rowCountries ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowCountries.forEach { country ->
                    Text(
                        text = countryToFlag(country),
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

private fun countryToFlag(country: String): String {
    return when (country.lowercase()) {
        "сша", "usa", "соединенные штаты" -> "🇺🇸"
        "великобритания", "uk", "соединенное королевство" -> "🇬🇧"
        "россия", "russia" -> "🇷🇺"
        "франция", "france" -> "🇫🇷"
        "германия", "germany" -> "🇩🇪"
        "италия", "italy" -> "🇮🇹"
        "испания", "spain" -> "🇪🇸"
        "канада", "canada" -> "🇨🇦"
        "австралия", "australia" -> "🇦🇺"
        "япония", "japan" -> "🇯🇵"
        "китай", "china" -> "🇨🇳"
        "корея", "korea" -> "🇰🇷"
        "индия", "india" -> "🇮🇳"
        "бразилия", "brazil" -> "🇧🇷"
        "мексика", "mexico" -> "🇲🇽"
        else -> "🏳️" // нейтральный флаг для неизвестных
    }
}