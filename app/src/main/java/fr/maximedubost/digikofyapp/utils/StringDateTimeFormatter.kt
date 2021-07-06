package fr.maximedubost.digikofyapp.utils

import android.annotation.SuppressLint
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class StringDateTimeFormatter {
    companion object {

        @SuppressLint("SimpleDateFormat")
        // val ISO_8601_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        // fun now(): String = ISO_8601_FORMAT.format(Date())

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

        fun weekdays(intList: ArrayList<Int>): String {
            if(intList.size == 7)
                return "Tous les jours"
            if(intList.size != 0) {
                var daysOfWeek = StringBuilder().append("Tous les ")
                intList.forEach {
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
    }
}