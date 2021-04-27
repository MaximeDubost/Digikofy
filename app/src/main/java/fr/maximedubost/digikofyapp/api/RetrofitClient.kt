package fr.maximedubost.digikofyapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.stackexchange.com/2.2/"

val retrofitClient : StackInterface by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return@lazy retrofit.create(StackInterface::class.java)
}