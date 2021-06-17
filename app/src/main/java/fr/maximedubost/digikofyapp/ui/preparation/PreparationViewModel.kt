package fr.maximedubost.digikofyapp.ui.preparation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PreparationViewModel : ViewModel() {
    val preparationResponseSuccess: MutableLiveData<Any> = MutableLiveData<Any>()
    val preparationResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
}