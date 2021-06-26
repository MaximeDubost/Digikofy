package fr.maximedubost.digikofyapp.api

import fr.maximedubost.digikofyapp.models.*
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {

    /**
     * Auth
     */

    @POST("register")
    suspend fun register(@Body registerRequestModel: RegisterRequestModel): Response<Any>

    @POST("login")
    suspend fun login(@Body LoginRequestModel: LoginRequestModel): Response<LoginResponseModel>

    @POST("revoke")
    suspend fun revoke(@Body refresh_token: HashMap<String,String>): Response<Any>

    @POST("refreshToken")
    suspend fun refreshToken(@Body refresh_token: HashMap<String,String>): Response<RefreshTokenModel>

    @DELETE("delete")
    suspend fun delete(@Header("Authorization") token: String): Response<Any>

    /**
     * Machine
     */

    @POST("machine")
    suspend fun createMachine(
        @Header("Authorization") token: String,
        @Body machineModel: MachineModel
    ): Response<Any>

    @GET("machines")
    suspend fun findAllMachines(
        @Header("Authorization") token: String
    ): Response<List<MachineModel>>

    @GET("machine/{id}")
    suspend fun findMachineById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<MachineModel>

    @PUT("machine/{id}")
    suspend fun updateMachine(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body machineModel: MachineModel
    ): Response<Any>

    @DELETE("machine/{id}")
    suspend fun deteleMachine(
        @Header("Authorization") token: String,
        @Path("id") id: String)
    : Response<Any>

    /**
     * Preparation
     */

    @POST("preparation")
    suspend fun createPreparation(
        @Header("Authorization") token: String,
        @Body preparationModel: PreparationModel
    ): Response<Any>

    @GET("preparations")
    suspend fun findAllPreparations(
        @Header("Authorization") token: String
    ): Response<List<PreparationModel>>

    @GET("preparation/{id}")
    suspend fun findPreparationById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<PreparationModel>

    @PUT("preparation/{id}")
    suspend fun updatePreparation(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body preparationModel: PreparationModel
    ): Response<Any>

    @DELETE("preparation/{id}")
    suspend fun detelePreparation(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Any>

    /**
     * Coffee
     */

    @GET("coffees")
    suspend fun findAllCoffees(): Response<List<CoffeeModel>>

    @GET("coffee/{id}")
    suspend fun findCoffeeById(@Path("id") id: String): Response<CoffeeModel>

}