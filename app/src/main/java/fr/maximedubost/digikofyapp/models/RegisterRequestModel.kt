package fr.maximedubost.digikofyapp.models

import com.google.gson.annotations.SerializedName

class RegisterRequestModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)