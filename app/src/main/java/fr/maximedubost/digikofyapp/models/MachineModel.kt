package fr.maximedubost.digikofyapp.models

import fr.maximedubost.digikofyapp.enums.MachineState
import fr.maximedubost.digikofyapp.enums.MachineType
import fr.maximedubost.digikofyapp.utils.StringDateTimeFormatter
import java.time.LocalDateTime
import java.util.*

data class MachineModel (
    val id: String = UUID.randomUUID().toString(),
    var name: String = UUID.randomUUID().toString().substring(24),
    val type: Int = MachineType.STANDARD.ordinal,
    var state: Int = MachineState.UNAVAILABLE.ordinal,
    val creationDate: String = LocalDateTime.now().toString(),
    var lastUpdate: String = LocalDateTime.now().toString(),
) {

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

    companion object {
        const val MACHINE_TYPE_STANDARD = "Modèle standard"
        const val MACHINE_TYPE_ENTERPRISE = "Modèle entreprise"
        const val MACHINE_STATE_AVAILABLE = "Disponible"
        const val MACHINE_STATE_IN_PROCESS = "En cours d'utilisation"
        const val MACHINE_STATE_UNAVAILABLE = "Indisponible"
    }

}

