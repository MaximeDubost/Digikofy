package fr.maximedubost.digikofyapp.repositories

import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.api.retrofitClient
import fr.maximedubost.digikofyapp.api.safeApiCall
import fr.maximedubost.digikofyapp.models.LoginRequestModel
import fr.maximedubost.digikofyapp.models.LoginResponseModel
import fr.maximedubost.digikofyapp.models.RegisterRequestModel
import retrofit2.Response

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

}