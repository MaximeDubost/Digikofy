package fr.maximedubost.digikofyapp.models

import java.util.*

data class Preparation (
    val id: String,
    val user: User,
    val moment: Date?,
)