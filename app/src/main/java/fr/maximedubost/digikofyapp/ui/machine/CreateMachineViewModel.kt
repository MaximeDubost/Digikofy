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

class CreateMachineViewModel : ViewModel() {
    val createMachineResponseSuccess: MutableLiveData<ApiResult.Success<Response<Any>>> =
        MutableLiveData<ApiResult.Success<Response<Any>>>()
    val createMachineResponseError: MutableLiveData<Any> = MutableLiveData<Any>()

    /**
     * Create
     */
    fun create(context: Context, machineModel: MachineModel) {
        viewModelScope.launch {
            when (val result = MachineRepository.create(context, machineModel)) {
                is ApiResult.Success -> createMachineResponseSuccess.postValue(result)
                is ApiResult.Error -> createMachineResponseError.postValue(result.exception)
            }
        }
    }
}