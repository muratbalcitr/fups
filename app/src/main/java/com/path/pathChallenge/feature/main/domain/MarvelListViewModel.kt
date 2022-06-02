package com.path.pathChallenge.feature.main.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.path.data.Status
import com.path.data.remote.response.CharactersResponse
import com.path.pathChallenge.BuildConfig
import com.path.pathChallenge.core.extensions.Event
import com.path.pathChallenge.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MarvelListViewModel @Inject constructor(
    val useCase: MarvelListUseCase
) : BaseViewModel() {

    private val _chrachterResponse = MutableLiveData<CharactersResponse>()
    val chrachterResponse: LiveData<CharactersResponse> = _chrachterResponse

    private val _event = MutableLiveData<Event<MarvelListViewEvent>>()
    val event: LiveData<Event<MarvelListViewEvent>> = _event

    private val _isMoreLoading = MutableLiveData<Boolean>()
    val isMoreLoading: LiveData<Boolean> = _isMoreLoading


    fun setMoreLoading(value: Boolean) = _isMoreLoading.postValue(value)

    fun fetchMarvelList(
        apiKey: String,
        limit: Int? = 30,
        offset: Int? = null,
        orderBy: String? = null
    ) {
        setLoading(true)
        useCase.fetchMarvelList(apiKey, BuildConfig.HASH_KEY, limit, offset, orderBy)
            .subscribe {

                when (it.status) {
                    Status.SUCCESS -> {
                        setLoading(false)
                        setMoreLoading(false)
                        it.data.let { res ->
                            _chrachterResponse.postValue(res)
                        }
                    }
                    Status.ERROR -> {
                        setLoading(false)
                        setMoreLoading(false)
                        Timber.e(it.error)
                    }
                    Status.LOADING -> {
                    }
                }
            }.let {
                disposable.add(it)
            }
    }

    fun selectCar(item: CharactersResponse.Data.Result) {
        _event.postValue(Event(MarvelListViewEvent.SelectMarvel(item)))
    }
}
