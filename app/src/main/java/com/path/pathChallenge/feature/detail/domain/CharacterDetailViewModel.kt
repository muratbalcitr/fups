package com.path.pathChallenge.feature.detail.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.path.data.Status
import com.path.data.remote.response.CharactersDetailResponse
import com.path.data.remote.response.ComicsResponse
import com.path.pathChallenge.core.extensions.Event
import com.path.pathChallenge.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    val useCase: CharacterDetailUseCase
    ) : BaseViewModel() {

    private val _characterDetail = MutableLiveData<CharactersDetailResponse>()
    val characterDetail: LiveData<CharactersDetailResponse> = _characterDetail

    private val _comicDetail = MutableLiveData<ComicsResponse>()
    val comicDetail: LiveData<ComicsResponse> = _comicDetail

    private val _event = MutableLiveData<Event<CharacterDetailViewEvent>>()
    val event: LiveData<Event<CharacterDetailViewEvent>> = _event

    val detailId = MutableLiveData<String>()

    fun fetchMarvelDetailList(
        apiKey: String,
        characterId: String
    ) {
        useCase.marvelCharacterDetailList(apiKey, characterId)
            .subscribe {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data.let { res ->
                            _characterDetail.postValue(res)
                        }
                    }
                    Status.ERROR -> {
                        Timber.e(it.error)
                    }
                }
            }.let {
                disposable.add(it)
            }
    }
    fun marvelCharacterComics(
        apiKey: String,
        characterId: String
    ) {
        useCase.marvelCharacterComics(apiKey, characterId)
            .subscribe {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data.let { res ->
                            _comicDetail.postValue(res)
                        }
                    }
                    Status.ERROR -> {
                        Timber.e(it.error)
                    }
                }
            }.let {
                disposable.add(it)
            }
    }
}
