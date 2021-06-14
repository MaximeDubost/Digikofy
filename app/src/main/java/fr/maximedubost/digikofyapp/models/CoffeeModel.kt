package fr.maximedubost.digikofyapp.models

import java.util.*

data class CoffeeModel(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "Café par défaut",
    val description: String = "Aucune description",
)