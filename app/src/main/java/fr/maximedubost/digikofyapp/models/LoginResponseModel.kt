package fr.maximedubost.digikofyapp.models

import com.google.gson.annotations.SerializedName

class LoginResponseModel(
    @SerializedName("idToken")
    val idToken: String,        // A Firebase Auth ID token for the authenticated user.
    @SerializedName("email")
    val email: String,          // The email for the authenticated user.
    @SerializedName("refreshToken")
    val refreshToken: String,   // A Firebase Auth refresh token for the authenticated user.
    @SerializedName("expiresIn")
    val expiresIn: String,      // The number of seconds in which the ID token expires.
    @SerializedName("localId")
    val localId: String,        // The uid of the authenticated user.
    @SerializedName("registered")
    val registered: Boolean     // Whether the email is for an existing account.
)