package fr.maximedubost.digikofyapp.repositories

import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.api.retrofitClient
import fr.maximedubost.digikofyapp.api.safeApiCall
import fr.maximedubost.digikofyapp.models.PreparationModel
import retrofit2.Response

object PreparationRepository {

    /**
     * Create a preparation
     * @param preparationModel Preparation object to create
     * @return HTTP status code
     */
    suspend fun create(preparationModel: PreparationModel): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.createPreparation(preparationModel)
    }

    /**
     * Read all preparations
     * @return preparation list
     */
    suspend fun findAll(): ApiResult<Response<List<PreparationModel>>> = safeApiCall {
        retrofitClient.findAllPreparations()
    }

    /**
     * Read a preparation
     * @param id Preparation id
     * @return Preparation object
     */
    suspend fun findById(id: String): ApiResult<Response<PreparationModel>> = safeApiCall {
        retrofitClient.findPreparationById(id)
    }

    /**
     * Update a preparation
     * @param id Preparation id
     * @return HTTP status code
     */
    suspend fun update(id: String): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.updatePreparation(id)
    }

    /**
     * Delete a preparation
     * @param id Preparation id
     * @return HTTP status code
     */
    suspend fun delete(id: String): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.detelePreparation(id)
    }

}