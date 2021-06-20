package fr.maximedubost.digikofyapp.ui.machine

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.models.LoginResponseModel
import fr.maximedubost.digikofyapp.models.MachineModel
import fr.maximedubost.digikofyapp.repositories.AuthRepository
import fr.maximedubost.digikofyapp.repositories.MachineRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MachineViewModel : ViewModel() {
    val machineResponseSuccess: MutableLiveData<ApiResult.Success<Response<List<MachineModel>>>> =
        MutableLiveData<ApiResult.Success<Response<List<MachineModel>>>>()
    val machineResponseError: MutableLiveData<Any> = MutableLiveData<Any>()

    /**
     * Find all
     */
    fun findAll(context: Context) {
        viewModelScope.launch {
            when (val result = MachineRepository.findAll(context)) {
                is ApiResult.Success -> machineResponseSuccess.postValue(result)
                is ApiResult.Error -> machineResponseError.postValue(result.exception)
            }
        }
    }

}