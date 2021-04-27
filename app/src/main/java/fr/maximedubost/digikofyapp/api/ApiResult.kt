package fr.maximedubost.digikofyapp.api

import android.util.Log
import retrofit2.Response

sealed class ApiResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val exception: String) : ApiResult<Nothing>()
}

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val myResp = call.invoke()
        when {
            myResp.isSuccessful -> {
                Log.d("plantLogs", "safeApiCall response body ${myResp.body()}")
                ApiResult.Success(myResp.body()!!)
            }
            else -> {
                Log.d("plantLogs", "safeApiCall error body ${myResp.errorBody()?.toString() ?: "Error"}")
                ApiResult.Error(myResp.errorBody()?.toString() ?: "Error")
            }
        }
    } catch (e: Exception) {
        Log.d("plantLogs", "safeApiCall error body ${e.message ?: "Internet error runs"}")
        ApiResult.Error(e.message ?: "Internet error runs")
    }
}