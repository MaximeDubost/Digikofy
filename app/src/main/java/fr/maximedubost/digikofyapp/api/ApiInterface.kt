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

    /**
     * Machine
     */

    @POST("machines")
    suspend fun createMachine(@Body machineModel: MachineModel): Response<Any>

    @GET("machines")
    suspend fun findAllMachines(): Response<List<MachineModel>>

    @GET("machines/{id}")
    suspend fun findMachineById(@Path("id") id: String): Response<MachineModel>

    @PUT("machines/{id}")
    suspend fun updateMachine(@Path("id") id: String): Response<Any>

    @DELETE("machines/{id}")
    suspend fun deteleMachine(@Path("id") id: String): Response<Any>

    /**
     * Preparation
     */

    @POST("preparations")
    suspend fun createPreparation(@Body preparationModel: PreparationModel): Response<Any>

    @GET("preparations")
    suspend fun findAllPreparations(): Response<List<PreparationModel>>

    @GET("preparations/{id}")
    suspend fun findPreparationById(@Path("id") id: String): Response<PreparationModel>

    @PUT("preparations/{id}")
    suspend fun updatePreparation(@Path("id") id: String): Response<Any>

    @DELETE("preparations/{id}")
    suspend fun detelePreparation(@Path("id") id: String): Response<Any>

    /**
     * Coffee
     */

    @POST("coffees")
    suspend fun createCoffee(@Body coffeeModel: CoffeeModel): Response<Any>

    @GET("coffees")
    suspend fun findAllCoffees(): Response<List<CoffeeModel>>

    @GET("coffees/{id}")
    suspend fun findCoffeeById(@Path("id") id: String): Response<CoffeeModel>

    @PUT("coffees/{id}")
    suspend fun updateCoffee(@Path("id") id: String): Response<Any>

    @DELETE("coffees/{id}")
    suspend fun deteleCoffee(@Path("id") id: String): Response<Any>
}