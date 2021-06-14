package fr.maximedubost.digikofyapp.models

import com.google.gson.annotations.SerializedName

class LoginRequestModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("returnSecureToken")
    val returnSecureToken: Boolean
)