package fr.maximedubost.digikofyapp.api

import fr.maximedubost.digikofyapp.models.LoginRequestModel
import fr.maximedubost.digikofyapp.models.LoginResponseModel
import fr.maximedubost.digikofyapp.models.RegisterRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {

    @POST("register")
    suspend fun register(@Body registerRequestModel: RegisterRequestModel): Response<Any>

    @POST("login")
    suspend fun login(@Body LoginRequestModel: LoginRequestModel): Response<LoginResponseModel>

}