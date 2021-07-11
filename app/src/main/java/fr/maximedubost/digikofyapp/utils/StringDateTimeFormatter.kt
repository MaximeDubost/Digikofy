package fr.maximedubost.digikofyapp.utils

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

class StringDateTimeFormatter {
    companion object {

        // val ISO_8601_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        // fun now(): String = ISO_8601_FORMAT.format(Date())

        /**
         * Return string representation of string timestamp
         *
         * @param stringDate string timestamp
         * @return a string representation
         */
        fun from(stringDate: String): String {
            if(stringDate.length == 25 &&
                stringDate[4] == '-' &&
                stringDate[7] == '-' &&
                stringDate[10] == 'T' &&
                stringDate[13] == ':' &&
                stringDate[16] == ':' &&
                stringDate[19] == '+' &&
                stringDate[22] == ':'
            ) {
                val year = stringDate.substring(0, 4)
                val month = stringDate.substring(5, 7)
                val day = stringDate.substring(8, 10)
                val hour = stringDate.substring(11, 13)
                val minute = stringDate.substring(14, 16)
                return "le $day/$month/$year à $hour:$minute"
            }
            return "Inconnue"
        }

        /**
         * Get a string representation of weekdays
         *
         * @param weekdayIndices list of weekday indices
         * @return a string representation of weekdays
         */
        fun weekdays(weekdayIndices: ArrayList<Int>): String {
            if(weekdayIndices.size == 7)
                return "Tous les jours"
            if(weekdayIndices.size != 0) {
                val daysOfWeek = StringBuilder().append("Tous les ")
                weekdayIndices.forEach {
                    when(it) {
                        0 -> daysOfWeek.append("Lun, ")
                        1 -> daysOfWeek.append("Mar, ")
                        2 -> daysOfWeek.append("Mer, ")
                        3 -> daysOfWeek.append("Jeu, ")
                        4 -> daysOfWeek.append("Ven, ")
                        5 -> daysOfWeek.append("Sam, ")
                        else -> daysOfWeek.append("Dim, ")
                    }
                }
                when(daysOfWeek.toString()) {
                    "Tous les Lun, " -> daysOfWeek.replace(9, 12, "lundis")
                    "Tous les Mar, " -> daysOfWeek.replace(9, 12, "mardis")
                    "Tous les Mer, " -> daysOfWeek.replace(9, 12, "mercredis")
                    "Tous les Jeu, " -> daysOfWeek.replace(9, 12, "jeudis")
                    "Tous les Ven, " -> daysOfWeek.replace(9, 12, "vendredis")
                    "Tous les Sam, " -> daysOfWeek.replace(9, 12, "samedis")
                    "Tous les Dim, " -> daysOfWeek.replace(9, 12, "dimanches")
                }
                return daysOfWeek.toString().substring(0, daysOfWeek.length-2)
            }
            return "Non planifié"
        }

        /**
         * Get a string representation of hours
         *
         * @param localTimeListToString list of string hours
         * @return a string representation of hours
         */
        fun hours(localTimeListToString: ArrayList<String>): String {
            if(localTimeListToString.size != 0) {
                val hours = StringBuilder()
                localTimeListToString.forEach {
                    hours.append(it.substring(0, 5) + ", ")
                }
                return hours.toString().substring(0, hours.length - 2)
            }
            return "Planifier"
        }

        /**
         * Get a string representation of period between two dates
         *
         * @param stringDate string date to compare with now
         * @param pastDate true if stringDate is a past date, else false (default false)
         * @return a string representation of calculated period
         */
        fun durationBetweenNowAnd(stringDate: String, pastDate: Boolean = false): String {
            lateinit var prefix: String
            lateinit var duration: Duration
            val instantDate = Instant.parse("${stringDate.substring(0, 16)}:00.00Z")
            val instantNow = Instant.parse("${LocalDateTime.now().toString().substring(0, 16)}:00.00Z")

            if(pastDate) {
                prefix = "Préparé il y a"
                duration = Duration.between(instantDate, instantNow)
            }
            else {
                prefix = "Préparation dans"
                duration = Duration.between(instantNow, instantDate)
            }

            if(duration.isNegative)
                return if(pastDate)
                    "Déjà préparé"
                else
                    "Pas encore préparé"

            val days = duration.toDays()
            val hours = duration.toHours() - duration.toDays() * 24
            val minutes = duration.toMinutes() - duration.toHours() * 60

            return when(0L) {
                minutes -> "$prefix un instant"
                hours -> "$prefix $minutes minutes"
                days -> "$prefix $hours heures"
                else -> "$prefix $days jours"
            }
        }

    }
}