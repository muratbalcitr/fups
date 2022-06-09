package com.fups.challenge.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    internal var disposable = CompositeDisposable()

    private fun disposeSubscriptions() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    fun setLoading(value: Boolean) = _isLoading.postValue(value)

    override fun onCleared() {
        disposeSubscriptions()
        super.onCleared()
    }
}
