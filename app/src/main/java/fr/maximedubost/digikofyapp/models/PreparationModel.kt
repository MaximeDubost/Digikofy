package fr.maximedubost.digikofyapp.models

import com.google.gson.annotations.SerializedName
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class PreparationModel (
    val id: String? = null,
    var coffee: CoffeeModel? = null,
    var saved: Boolean = false,
    @SerializedName("next_time")
    var nextTime: String? = null,
    @SerializedName("last_time")
    var lastTime: String? = null,
    var name: String? = null,
    @SerializedName("days_of_week")
    var weekdays: ArrayList<Int>? = null,
    var hours: ArrayList<String>? = null,
    @SerializedName("creation_date")
    val creationDate: String? = null,
    @SerializedName("last_update")
    var lastUpdate: String? = null
) {

    fun isFuturePreparation(): Boolean {
        if(this.nextTime != null)
        {
            val nextTime = LocalDateTime.parse(this.nextTime!!.substring(0, this.nextTime!!.length-6))

            return nextTime.isAfter(LocalDateTime.now())
        }
        return false
    }


}