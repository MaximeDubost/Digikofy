package fr.maximedubost.digikofyapp.api

import android.util.Log
import retrofit2.Response

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val exception: String) : ApiResult<Nothing>()
}

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): ApiResult<Response<T>> {
    return try {
        val myResp = call.invoke()
        Log.d("BeforeWhen", myResp.toString())
        when {
            myResp.isSuccessful -> {
                Log.d("Success", "safeApiCall response body $myResp")
                ApiResult.Success(myResp)
            }
            else -> {
                Log.d(
                    "Failure",
                    "safeApiCall error body ${myResp.errorBody()?.toString() ?: "Error"}"
                )
                ApiResult.Error(myResp.errorBody()?.toString() ?: "Error")
            }
        }
    } catch (e: Exception) {
        Log.d("Error", "safeApiCall error body ${e.message ?: "Internet error runs"}")
        ApiResult.Error(e.message ?: "Internet error runs")
    }
}