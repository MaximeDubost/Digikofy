package fr.maximedubost.digikofyapp.models

import java.util.*

data class User (
    val id: String,
    var email: String?,
    var password: String?,
    val creationDate: Date?,
    var lastConnection: Date?
)