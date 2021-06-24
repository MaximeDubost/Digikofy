package fr.maximedubost.digikofyapp.ui.machine

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.repositories.MachineRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MachineViewModel : ViewModel() {
    val machineFindAllResponseSuccess: MutableLiveData<ApiResult.Success<Response<List<MachineModel>>>> = MutableLiveData<ApiResult.Success<Response<List<MachineModel>>>>()
    val machineFindAllResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
    val machineFindByIdResponseSuccess: MutableLiveData<ApiResult.Success<Response<MachineModel>>> = MutableLiveData<ApiResult.Success<Response<MachineModel>>>()
    val machineFindByIdResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
    val machineCreateResponseSuccess: MutableLiveData<ApiResult.Success<Response<Any>>> = MutableLiveData<ApiResult.Success<Response<Any>>>()
    val machineCreateResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
    val machineUpdateResponseSuccess: MutableLiveData<ApiResult.Success<Response<Any>>> = MutableLiveData<ApiResult.Success<Response<Any>>>()
    val machineUpdateResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
    val machineDeleteResponseSuccess: MutableLiveData<ApiResult.Success<Response<Any>>> = MutableLiveData<ApiResult.Success<Response<Any>>>()
    val machineDeleteResponseError: MutableLiveData<Any> = MutableLiveData<Any>()

    /**
     * Find all
     */
    fun findAll(context: Context) {
        viewModelScope.launch {
            when (val result = MachineRepository.findAll(context)) {
                is ApiResult.Success -> machineFindAllResponseSuccess.postValue(result)
                is ApiResult.Error -> machineFindAllResponseError.postValue(result.exception)
            }
        }
    }

    /**
     * Find by id
     */
    fun findById(context: Context, machineId: String) {
        viewModelScope.launch {
            when (val result = MachineRepository.findById(context, machineId)) {
                is ApiResult.Success -> machineFindByIdResponseSuccess.postValue(result)
                is ApiResult.Error -> machineFindByIdResponseError.postValue(result.exception)
            }
        }
    }

    /**
     * Create
     */
    fun create(context: Context, machineModel: MachineModel) {
        viewModelScope.launch {
            when (val result = MachineRepository.create(context, machineModel)) {
                is ApiResult.Success -> machineCreateResponseSuccess.postValue(result)
                is ApiResult.Error -> machineCreateResponseError.postValue(result.exception)
            }
        }
    }

    /**
     * Update
     */
    fun update(context: Context, machineModel: MachineModel) {
        viewModelScope.launch {
            when (val result = MachineRepository.update(context, machineModel)) {
                is ApiResult.Success -> machineUpdateResponseSuccess.postValue(result)
                is ApiResult.Error -> machineUpdateResponseError.postValue(result.exception)
            }
        }
    }

    /**
     * Delete
     */
    fun delete(context: Context, machineId: String) {
        viewModelScope.launch {
            when (val result = MachineRepository.delete(context, machineId)) {
                is ApiResult.Success -> machineDeleteResponseSuccess.postValue(result)
                is ApiResult.Error -> machineDeleteResponseError.postValue(result.exception)
            }
        }
    }

}