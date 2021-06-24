package fr.maximedubost.digikofyapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.models.RefreshTokenModel
import fr.maximedubost.digikofyapp.repositories.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel : ViewModel() {
    val mainResponseSuccess: MutableLiveData<ApiResult.Success<Response<RefreshTokenModel>>> =
        MutableLiveData<ApiResult.Success<Response<RefreshTokenModel>>>()
    val mainResponseError: MutableLiveData<Any> = MutableLiveData<Any>()

    /**
     * RefreshToken
     * @param refreshToken refreshToken
     */
    fun refreshToken(refreshToken: String) {
        viewModelScope.launch {
            when (val result = AuthRepository.refreshToken(hashMapOf("refresh_token" to refreshToken))) {
                is ApiResult.Success -> mainResponseSuccess.postValue(result)
                is ApiResult.Error -> mainResponseError.postValue(result.exception)
            }
        }
    }
}