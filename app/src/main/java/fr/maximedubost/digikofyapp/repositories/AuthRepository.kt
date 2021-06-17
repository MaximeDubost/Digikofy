package fr.maximedubost.digikofyapp.repositories

import android.content.Context
import fr.maximedubost.digikofyapp.MainActivity
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.api.retrofitClient
import fr.maximedubost.digikofyapp.api.safeApiCall
import fr.maximedubost.digikofyapp.models.LoginRequestModel
import fr.maximedubost.digikofyapp.models.LoginResponseModel
import fr.maximedubost.digikofyapp.models.RefreshTokenModel
import fr.maximedubost.digikofyapp.models.RegisterRequestModel
import fr.maximedubost.digikofyapp.session.DigikofySession
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

object AuthRepository {

    /**
     * Register
     */
    suspend fun register(email: String, password: String): ApiResult<Any> = safeApiCall {
        retrofitClient.register(RegisterRequestModel(email, password))
    }

    /**
     * Login
     */
    suspend fun login(email: String, password: String): ApiResult<Response<LoginResponseModel>> = safeApiCall {
        retrofitClient.login(LoginRequestModel(email, password, true))
    }

    /**
     * Revoke
     */
    suspend fun revoke(refreshToken: String): ApiResult<Any> = safeApiCall {
        retrofitClient.revoke(refreshToken)
    }

    /**
     * RefreshToken
     */
    suspend fun refreshToken(refreshToken: String): ApiResult<Response<RefreshTokenModel>> = safeApiCall {
        retrofitClient.refreshToken(refreshToken)
    }

    /**
     * Delete
     */
    suspend fun delete(context: Context): ApiResult<Any> = safeApiCall {
        retrofitClient.delete("Bearer ${DigikofySession.getIdToken(context)!!}")
    }

}