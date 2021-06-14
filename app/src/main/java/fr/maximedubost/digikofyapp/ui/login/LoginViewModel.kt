package fr.maximedubost.digikofyapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.repositories.RemoteRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val loginResponseSuccess: MutableLiveData<Any> = MutableLiveData<Any>()
    val loginResponseError: MutableLiveData<Any> = MutableLiveData<Any>()

    /**
     * Login
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            when (val result = RemoteRepository.login(email, password)) {
                is ApiResult.Success -> loginResponseSuccess.postValue(result)
                is ApiResult.Error -> loginResponseError.postValue(result.exception)
            }
        }
    }

}