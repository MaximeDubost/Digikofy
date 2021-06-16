package fr.maximedubost.digikofyapp.models

import com.google.gson.annotations.SerializedName

class RefreshTokenModel (
    @SerializedName("id_token")
    val idToken: String,        // A Firebase Auth ID token for the newly created user.
    @SerializedName("email")
    val email: String,          // The email for the newly created user.
    @SerializedName("refresh_token")
    val refreshToken: String,   // A Firebase Auth refresh token for the newly created user.
    @SerializedName("expires_in")
    val expiresIn: String,      // The number of seconds in which the ID token expires.
    @SerializedName("local_id")
    val localId: String         // The uid of the newly created user.
)