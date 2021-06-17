package fr.maximedubost.digikofyapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// const val BASE_URL = "http://192.168.1.14:8000/"
const val BASE_URL = "https://digikofy-api.herokuapp.com/"

val retrofitClient: ApiInterface by lazy {

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return@lazy retrofit.create(ApiInterface::class.java)

}