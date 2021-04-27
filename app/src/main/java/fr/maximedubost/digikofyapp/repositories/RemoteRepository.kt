package fr.maximedubost.digikofyapp.repositories

import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.api.retrofitClient
import fr.maximedubost.digikofyapp.api.safeApiCall

object RemoteRepository {
    suspend fun getLastQuestions(): ApiResult<Any> =
        safeApiCall { retrofitClient.getLastQuestions() }
}