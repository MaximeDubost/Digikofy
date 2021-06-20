package fr.maximedubost.digikofyapp.models

import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class PreparationModel (
    val id: String = UUID.randomUUID().toString().substring(20),
    var coffee: CoffeeModel = CoffeeModel(UUID.randomUUID().toString(), "Café par défaut", "Aucune description"),
    var saved: Boolean = false,
    var nextTime: String = "0001-01-01T00:00:00Z",
    var lastTime: String? = null,
    var name: String? = null,
    var daysOfWeek: ArrayList<Int>? = null,
    var hours: ArrayList<String>? = null,
    val creationDate: String = LocalDateTime.now().toString(),
    var lastUpdate: String = LocalDateTime.now().toString(),
) {

    fun isFuturePreparation(): Boolean =
        LocalDateTime.parse(this.nextTime, DateTimeFormatter
            .ofPattern(StringDateTimeFormatter.ISO_8601_FORMAT.toPattern())
        ).isAfter(LocalDateTime.now())
}