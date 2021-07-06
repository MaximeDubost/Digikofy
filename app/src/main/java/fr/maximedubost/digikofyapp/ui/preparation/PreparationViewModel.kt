package fr.maximedubost.digikofyapp.ui.preparation

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.maximedubost.digikofyapp.api.ApiResult
import fr.maximedubost.digikofyapp.models.PreparationModel
import fr.maximedubost.digikofyapp.repositories.PreparationRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class PreparationViewModel : ViewModel() {
    val preparationFindAllResponseSuccess: MutableLiveData<ApiResult.Success<Response<List<PreparationModel>>>> = MutableLiveData<ApiResult.Success<Response<List<PreparationModel>>>>()
    val preparationFindAllResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
    val preparationFindByIdResponseSuccess: MutableLiveData<ApiResult.Success<Response<PreparationModel>>> = MutableLiveData<ApiResult.Success<Response<PreparationModel>>>()
    val preparationFindByIdResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
    val preparationCreateResponseSuccess: MutableLiveData<ApiResult.Success<Response<Any>>> = MutableLiveData<ApiResult.Success<Response<Any>>>()
    val preparationCreateResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
    val preparationUpdateResponseSuccess: MutableLiveData<ApiResult.Success<Response<Any>>> = MutableLiveData<ApiResult.Success<Response<Any>>>()
    val preparationUpdateResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
    val preparationDeleteResponseSuccess: MutableLiveData<ApiResult.Success<Response<Any>>> = MutableLiveData<ApiResult.Success<Response<Any>>>()
    val preparationDeleteResponseError: MutableLiveData<Any> = MutableLiveData<Any>()

    /**
     * Find all
     */
    fun findAll(context: Context) {
        viewModelScope.launch {
            when (val result = PreparationRepository.findAll(context)) {
                is ApiResult.Success -> preparationFindAllResponseSuccess.postValue(result)
                is ApiResult.Error -> preparationFindAllResponseError.postValue(result.exception)
            }
        }
    }

    /**
     * Find by id
     */
    fun findById(context: Context, preparationId: String) {
        viewModelScope.launch {
            when (val result = PreparationRepository.findById(context, preparationId)) {
                is ApiResult.Success -> preparationFindByIdResponseSuccess.postValue(result)
                is ApiResult.Error -> preparationFindByIdResponseError.postValue(result.exception)
            }
        }
    }

    /**
     * Create
     */
    fun create(context: Context, preparationModel: PreparationModel) {
        viewModelScope.launch {
            when (val result = PreparationRepository.create(context, preparationModel)) {
                is ApiResult.Success -> preparationCreateResponseSuccess.postValue(result)
                is ApiResult.Error -> preparationCreateResponseError.postValue(result.exception)
            }
        }
    }

    /**
     * Update
     */
    fun update(context: Context, preparationModel: PreparationModel) {
        viewModelScope.launch {
            when (val result = PreparationRepository.update(context, preparationModel)) {
                is ApiResult.Success -> preparationUpdateResponseSuccess.postValue(result)
                is ApiResult.Error -> preparationUpdateResponseError.postValue(result.exception)
            }
        }
    }

    /**
     * Delete
     */
    fun delete(context: Context, preparationId: String) {
        viewModelScope.launch {
            when (val result = PreparationRepository.delete(context, preparationId)) {
                is ApiResult.Success -> preparationDeleteResponseSuccess.postValue(result)
                is ApiResult.Error -> preparationDeleteResponseError.postValue(result.exception)
            }
        }
    }
}