package fr.maximedubost.digikofyapp.repositories

import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.api.retrofitClient
import fr.maximedubost.digikofyapp.api.safeApiCall
import fr.maximedubost.digikofyapp.models.MachineModel
import retrofit2.Response

object MachineRepository {

    /**
     * Create a machine
     * @param machineModel Machine object to create
     * @return HTTP status code
     */
    suspend fun create(machineModel: MachineModel): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.createMachine(machineModel)
    }

    /**
     * Read all machines
     * @return machine list
     */
    suspend fun findAll(): ApiResult<Response<List<MachineModel>>> = safeApiCall {
        retrofitClient.findAllMachines()
    }

    /**
     * Read a machine
     * @param id Machine id
     * @return Machine object
     */
    suspend fun findById(id: String): ApiResult<Response<MachineModel>> = safeApiCall {
        retrofitClient.findMachineById(id)
    }

    /**
     * Update a machine
     * @param id Machine id
     * @return HTTP status code
     */
    suspend fun update(id: String): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.updateMachine(id)
    }

    /**
     * Delete a machine
     * @param id Machine id
     * @return HTTP status code
     */
    suspend fun delete(id: String): ApiResult<Response<Any>> = safeApiCall {
        retrofitClient.deteleMachine(id)
    }

}