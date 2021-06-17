package fr.maximedubost.digikofyapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.models.LoginResponseModel
import fr.maximedubost.digikofyapp.repositories.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val loginResponseSuccess: MutableLiveData<ApiResult.Success<Response<LoginResponseModel>>> =
        MutableLiveData<ApiResult.Success<Response<LoginResponseModel>>>()
    val loginResponseError: MutableLiveData<Any> = MutableLiveData<Any>()

    /**
     * Login
     * @param email email
     * @param password password
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            when (val result = AuthRepository.login(email, password)) {
                is ApiResult.Success -> loginResponseSuccess.postValue(result)
                is ApiResult.Error -> loginResponseError.postValue(result.exception)
            }
        }
    }

}