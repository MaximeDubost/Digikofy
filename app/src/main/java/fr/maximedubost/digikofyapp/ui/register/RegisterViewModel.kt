package fr.maximedubost.digikofyapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.repositories.RemoteRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    val registerResponseSuccess: MutableLiveData<Any> = MutableLiveData<Any>()
    val registerResponseError: MutableLiveData<Any> = MutableLiveData<Any>()

    /**
     * register
     */
    fun register(email: String, password: String) {
        viewModelScope.launch {
            when (val result = RemoteRepository.register(email, password)) {
                is ApiResult.Success -> registerResponseSuccess.postValue(result)
                is ApiResult.Error -> registerResponseError.postValue(result.exception)
            }
        }
    }
}