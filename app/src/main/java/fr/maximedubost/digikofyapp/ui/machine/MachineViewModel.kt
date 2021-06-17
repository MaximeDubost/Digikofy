package fr.maximedubost.digikofyapp.ui.machine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MachineViewModel : ViewModel() {
    val machineResponseSuccess: MutableLiveData<Any> = MutableLiveData<Any>()
    val machineResponseError: MutableLiveData<Any> = MutableLiveData<Any>()
}