package fr.maximedubost.digikofyapp.models

import java.util.*

data class UserModel (
    val id: String,
    var email: String?,
    var password: String?,
    val creationDate: Date?,
    var lastConnection: Date?
)