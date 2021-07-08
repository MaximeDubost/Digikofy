package fr.maximedubost.digikofyapp.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.models.PreparationModel
import fr.maximedubost.digikofyapp.repositories.PreparationRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel : ViewModel() {
    val preparationFindNextResponseSuccess: MutableLiveData<ApiResult.Success<Response<PreparationModel>>> = MutableLiveData<ApiResult.Success<Response<PreparationModel>>>()
    val preparationFindNextResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
    val preparationFindLastResponseSuccess: MutableLiveData<ApiResult.Success<Response<PreparationModel>>> = MutableLiveData<ApiResult.Success<Response<PreparationModel>>>()
    val preparationFindLastResponseError: MutableLiveData<Any> = MutableLiveData<Any>()

    fun findNext(context: Context) {
        viewModelScope.launch {
            when (val result = PreparationRepository.findNext(context)) {
                is ApiResult.Success -> preparationFindNextResponseSuccess.postValue(result)
                is ApiResult.Error -> preparationFindNextResponseError.postValue(result.exception)
            }
        }
    }

    fun findLast(context: Context) {
        viewModelScope.launch {
            when (val result = PreparationRepository.findLast(context)) {
                is ApiResult.Success -> preparationFindLastResponseSuccess.postValue(result)
                is ApiResult.Error -> preparationFindLastResponseError.postValue(result.exception)
            }
        }
    }
}