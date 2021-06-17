package fr.maximedubost.digikofyapp.repositories

import android.content.Context
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.api.retrofitClient
import fr.maximedubost.digikofyapp.api.safeApiCall
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.session.DigikofySession
import retrofit2.Response

object MachineRepository {

    /**
     * Create a machine
     * @param machineModel Machine object to create
     * @return HTTP status code
     */
    suspend fun create(context: Context, machineModel: MachineModel): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.createMachine(DigikofySession.getIdToken(context)!!, machineModel)
    }

    /**
     * Read all machines
     * @return machine list
     */
    suspend fun findAll(context: Context): ApiResult<Response<List<MachineModel>>> = safeApiCall {
        retrofitClient.findAllMachines(DigikofySession.getIdToken(context)!!)
    }

    /**
     * Read a machine
     * @param id Machine id
     * @return Machine object
     */
    suspend fun findById(context: Context, id: String): ApiResult<Response<MachineModel>> = safeApiCall {
        retrofitClient.findMachineById(DigikofySession.getIdToken(context)!!, id)
    }

    /**
     * Update a machine
     * @param id Machine id
     * @return HTTP status code
     */
    suspend fun update(context: Context, id: String): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.updateMachine(DigikofySession.getIdToken(context)!!, id)
    }

    /**
     * Delete a machine
     * @param id Machine id
     * @return HTTP status code
     */
    suspend fun delete(context: Context, id: String): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.deteleMachine(DigikofySession.getIdToken(context)!!, id)
    }

}