package fr.maximedubost.digikofyapp.ui.user

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.repositories.AuthRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    val userResponseSuccess: MutableLiveData<Any> = MutableLiveData<Any>()
    val userResponseError: MutableLiveData<Any> = MutableLiveData<Any>()

    /**
     * Revoke
     * @param refreshToken refreshToken
     */
    fun revoke(refreshToken: String) {
        viewModelScope.launch {
            when (val result = AuthRepository.revoke(hashMapOf("refresh_token" to refreshToken))) {
                is ApiResult.Success -> userResponseSuccess.postValue(result)
                is ApiResult.Error -> userResponseError.postValue(result.exception)
            }
        }
    }

    /**
     * Delete
     * @param context context
     */
    fun delete(context: Context) {
        viewModelScope.launch {
            when (val result = AuthRepository.delete(context)) {
                is ApiResult.Success -> userResponseSuccess.postValue(result)
                is ApiResult.Error -> userResponseError.postValue(result.exception)
            }
        }
    }

}