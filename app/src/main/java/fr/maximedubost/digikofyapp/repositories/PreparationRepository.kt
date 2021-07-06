package fr.maximedubost.digikofyapp.repositories

import android.content.Context
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.api.retrofitClient
import fr.maximedubost.digikofyapp.api.safeApiCall
import fr.maximedubost.digikofyapp.models.PreparationModel
import fr.maximedubost.digikofyapp.session.DigikofySession
import retrofit2.Response

object PreparationRepository {

    /**
     * Create a preparation
     * @param preparationModel Preparation object to create
     * @return HTTP status code
     */
    suspend fun create(context: Context, preparationModel: PreparationModel): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.createPreparation("Bearer ${DigikofySession.getIdToken(context)!!}", preparationModel)
    }

    /**
     * Read all preparations
     * @return preparation list
     */
    suspend fun findAll(context: Context): ApiResult<Response<List<PreparationModel>>> = safeApiCall {
        retrofitClient.findAllPreparations("Bearer ${DigikofySession.getIdToken(context)!!}")
    }

    /**
     * Read a preparation
     * @param id Preparation id
     * @return Preparation object
     */
    suspend fun findById(context: Context, id: String): ApiResult<Response<PreparationModel>> = safeApiCall {
        retrofitClient.findPreparationById("Bearer ${DigikofySession.getIdToken(context)!!}", id)
    }

    /**
     * Update a preparation
     * @param id Preparation id
     * @return HTTP status code
     */
    suspend fun update(context: Context, preparationModel: PreparationModel): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.updatePreparation("Bearer ${DigikofySession.getIdToken(context)!!}", preparationModel.id!!, preparationModel)
    }

    /**
     * Delete a preparation
     * @param id Preparation id
     * @return HTTP status code
     */
    suspend fun delete(context: Context, id: String): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.detelePreparation("Bearer ${DigikofySession.getIdToken(context)!!}", id)
    }

}