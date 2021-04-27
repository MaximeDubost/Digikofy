package fr.maximedubost.digikofyapp.api

import android.telecom.Call
import retrofit2.Response
import retrofit2.http.GET

interface StackInterface {

    @GET("questions?order=desc&sort=activity&site=stackoverflow")
    suspend fun getLastQuestions() : Response<Any>

}