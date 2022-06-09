package com.fups.challenge.feature.login.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.fups.challenge.core.extensions.Event
import com.fups.challenge.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val _event = MutableLiveData<Event<LoginViewEvent>>()
    val event: LiveData<Event<LoginViewEvent>> = _event

    val phoneNumberFirst = MutableLiveData<String>()
    val phoneNumberLast = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val enabled: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun validateInputs(): Boolean {
            return (
                    !phoneNumberFirst.value.isNullOrEmpty() &&
                            !phoneNumberLast.value.isNullOrEmpty() &&
                            !password.value.isNullOrEmpty()
                    )
        }
        addSource(phoneNumberLast) { value = validateInputs() }
        addSource(phoneNumberFirst) { value = validateInputs() }
        addSource(password) { value = validateInputs() }
    }

    fun navigateToMain() {
        _event.postValue(Event(LoginViewEvent.NavigateToMain))
    }
    fun getInfo() = _event.postValue(Event(LoginViewEvent.GetInfo))
}