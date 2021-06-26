package fr.maximedubost.digikofyapp.models

import com.google.gson.annotations.SerializedName
import com.google.type.DateTime
import fr.maximedubost.digikofyapp.enums.MachineState
import fr.maximedubost.digikofyapp.enums.MachineType
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter
import java.time.LocalDateTime
import java.util.*

data class MachineModel (
    val id: String? = null,
    var name: String? = null,
    val type: Int? = null,
    var state: Int? = null,
    @SerializedName("creation_date")
    val creationDate: String? = null,
    @SerializedName("last_update")
    var lastUpdate: String? = null
) {

    companion object {
        const val MACHINE_TYPE_STANDARD = "Modèle standard"
        const val MACHINE_TYPE_ENTERPRISE = "Modèle entreprise"
        const val MACHINE_STATE_AVAILABLE = "Disponible"
        const val MACHINE_STATE_IN_PROCESS = "En cours d'utilisation"
        const val MACHINE_STATE_UNAVAILABLE = "Indisponible"

        fun typeToString(type: Int) = when(type) {
            0 -> MACHINE_TYPE_STANDARD
            1 -> MACHINE_TYPE_ENTERPRISE
            else -> MACHINE_TYPE_STANDARD
        }

        fun stateToString(state: Int) = when(state) {
            0 -> MACHINE_STATE_AVAILABLE
            1 -> MACHINE_STATE_IN_PROCESS
            2 -> MACHINE_STATE_UNAVAILABLE
            else -> MACHINE_STATE_UNAVAILABLE
        }
    }

    fun typeToString() = when(this.type) {
        0 -> MACHINE_TYPE_STANDARD
        1 -> MACHINE_TYPE_ENTERPRISE
        else -> MACHINE_TYPE_STANDARD
    }

    fun stateToString() = when(this.state) {
        0 -> MACHINE_STATE_AVAILABLE
        1 -> MACHINE_STATE_IN_PROCESS
        2 -> MACHINE_STATE_UNAVAILABLE
        else -> MACHINE_STATE_UNAVAILABLE
    }

}

