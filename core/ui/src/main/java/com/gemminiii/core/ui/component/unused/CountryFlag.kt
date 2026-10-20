package com.gemminiii.core.ui.component.unused

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun CountryFlagsGrid(countries: List<String>) {
    val displayCountries = countries.take(4)
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        displayCountries.chunked(2).forEach { rowCountries ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowCountries.forEach { country ->
                    FlagItem(country)
                }
            }
        }
        if(displayCountries.size < 3){
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FlagItem(null, invisible = true) // ← невидимый флаг
            }
        }
    }
}

@Composable
private fun FlagItem(country: String?, invisible: Boolean = false) {
    Text(
        text = if (country != null) countryToFlag(country) else "🏳️",
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier
            .alpha(if (invisible) 0f else 1f) // ← невидимый, но занимает место
    )
}

private fun countryToFlag(country: String): String {
    return when (country.lowercase().trim()) {
        "сша", "usa", "соединенные штаты", "united states" -> "🇺🇸"
        "канада", "canada" -> "🇨🇦"
        "мексика", "mexico" -> "🇲🇽"
        "куба", "cuba" -> "🇨🇺"
        "ямайка", "jamaica" -> "🇯🇲"
        "гаити", "haiti" -> "🇭🇹"
        "доминикана", "доминиканская республика" -> "🇩🇴"
        "панама", "panama" -> "🇵🇦"
        "коста-рика", "costa rica" -> "🇨🇷"
        "гватемала", "guatemala" -> "🇬🇹"
        "гондурас", "honduras" -> "🇭🇳"
        "сальвадор", "el salvador" -> "🇸🇻"
        "никарагуа", "nicaragua" -> "🇳🇮"
        "бразилия", "brazil" -> "🇧🇷"
        "аргентина", "argentina" -> "🇦🇷"
        "чили", "chile" -> "🇨🇱"
        "колумбия", "colombia" -> "🇨🇴"
        "перу", "peru" -> "🇵🇪"
        "венесуэла", "venezuela" -> "🇻🇪"
        "эквадор", "ecuador" -> "🇪🇨"
        "боливия", "bolivia" -> "🇧🇴"
        "парагвай", "paraguay" -> "🇵🇾"
        "уругвай", "uruguay" -> "🇺🇾"
        "великобритания", "uk", "соединенное королевство", "united kingdom", "англия" -> "🇬🇧"
        "россия", "russia", "рф", "ссср", "soviet union", "ussr" -> "🇷🇺"
        "франция", "france" -> "🇫🇷"
        "германия", "germany" -> "🇩🇪"
        "италия", "italy" -> "🇮🇹"
        "испания", "spain" -> "🇪🇸"
        "португалия", "portugal" -> "🇵🇹"
        "нидерланды", "голландия", "netherlands" -> "🇳🇱"
        "бельгия", "belgium" -> "🇧🇪"
        "швейцария", "switzerland" -> "🇨🇭"
        "австрия", "austria" -> "🇦🇹"
        "швеция", "sweden" -> "🇸🇪"
        "норвегия", "norway" -> "🇳🇴"
        "дания", "denmark" -> "🇩🇰"
        "финляндия", "finland" -> "🇫🇮"
        "исландия", "iceland" -> "🇮🇸"
        "ирландия", "ireland" -> "🇮🇪"
        "польша", "poland" -> "🇵🇱"
        "чехия", "czechia", "czech republic" -> "🇨🇿"
        "словакия", "slovakia" -> "🇸🇰"
        "венгрия", "hungary" -> "🇭🇺"
        "румыния", "romania" -> "🇷🇴"
        "болгария", "bulgaria" -> "🇧🇬"
        "греция", "greece" -> "🇬🇷"
        "хорватия", "croatia" -> "🇭🇷"
        "сербия", "serbia" -> "🇷🇸"
        "словения", "slovenia" -> "🇸🇮"
        "украина", "ukraine" -> "🇺🇦"
        "беларусь", "белоруссия", "belarus" -> "🇧🇾"
        "молдова", "moldova" -> "🇲🇩"
        "литва", "lithuania" -> "🇱🇹"
        "латвия", "latvia" -> "🇱🇻"
        "эстония", "estonia" -> "🇪🇪"
        "грузия", "georgia" -> "🇬🇪"
        "армения", "armenia" -> "🇦🇲"
        "азербайджан", "azerbaijan" -> "🇦🇿"
        "люксембург", "luxembourg" -> "🇱🇺"
        "мальта", "malta" -> "🇲🇹"
        "кипр", "cyprus" -> "🇨🇾"
        "япония", "japan" -> "🇯🇵"
        "китай", "china", "кнр" -> "🇨🇳"
        "корея", "южная корея", "korea", "south korea" -> "🇰🇷"
        "кндр", "северная корея", "north korea" -> "🇰🇵"
        "индия", "india" -> "🇮🇳"
        "пакистан", "pakistan" -> "🇵🇰"
        "бангладеш", "bangladesh" -> "🇧🇩"
        "вьетнам", "vietnam" -> "🇻🇳"
        "таиланд", "thailand" -> "🇹🇭"
        "индонезия", "indonesia" -> "🇮🇩"
        "малайзия", "malaysia" -> "🇲🇾"
        "сингапур", "singapore" -> "🇸🇬"
        "филиппины", "philippines" -> "🇵🇭"
        "турция", "turkey", "türkiye" -> "🇹🇷"
        "израиль", "israel" -> "🇮🇱"
        "саудовская аравия", "saudi arabia" -> "🇸🇦"
        "оаэ", "эмираты", "uae", "united arab emirates" -> "🇦🇪"
        "иран", "iran" -> "🇮🇷"
        "ирак", "iraq" -> "🇮🇶"
        "казахстан", "kazakhstan" -> "🇰🇿"
        "узбекистан", "uzbekistan" -> "🇺🇿"
        "монголия", "mongolia" -> "🇲🇳"
        "непал", "nepal" -> "🇳🇵"
        "шри-ланка", "sri lanka" -> "🇱🇰"
        "мьянма", "myanmar" -> "🇲🇲"
        "камбоджа", "cambodia" -> "🇰🇭"
        "лаос", "laos" -> "🇱🇦"
        "египет", "egypt" -> "🇪🇬"
        "юар", "южно-африканская республика", "south africa" -> "🇿🇦"
        "нигерия", "nigeria" -> "🇳🇬"
        "кения", "kenya" -> "🇰🇪"
        "эфиопия", "ethiopia" -> "🇪🇹"
        "марокко", "morocco" -> "🇲🇦"
        "алжир", "algeria" -> "🇩🇿"
        "тунис", "tunisia" -> "🇹🇳"
        "гана", "ghana" -> "🇬🇭"
        "танзания", "tanzania" -> "🇹🇿"
        "уганда", "uganda" -> "🇺🇬"
        "сенегал", "senegal" -> "🇸🇳"
        "австралия", "australia" -> "🇦🇺"
        "новая зеландия", "new zealand" -> "🇳🇿"
        "фиджи", "fiji" -> "🇫🇯"
        "папуа-новая гвинея", "papua new guinea" -> "🇵🇬"

        // --- Неизвестные ---
        else -> "🏳️" // нейтральный флаг
    }
}