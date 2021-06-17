package fr.maximedubost.digikofyapp.repositories

import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.api.retrofitClient
import fr.maximedubost.digikofyapp.api.safeApiCall
import fr.maximedubost.digikofyapp.models.CoffeeModel
import retrofit2.Response

object CoffeeRepository {

    /**
     * Read all coffees
     * @return coffee list
     */
    suspend fun findAll(): ApiResult<Response<List<CoffeeModel>>> = safeApiCall {
        retrofitClient.findAllCoffees()
    }

    /**
     * Read a coffee
     * @param id Coffee id
     * @return Coffee object
     */
    suspend fun findById(id: String): ApiResult<Response<CoffeeModel>> = safeApiCall {
        retrofitClient.findCoffeeById(id)
    }

}