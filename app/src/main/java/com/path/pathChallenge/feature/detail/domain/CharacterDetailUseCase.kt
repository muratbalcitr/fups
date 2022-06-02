package com.path.pathChallenge.feature.detail.domain

import com.path.data.Resource
import com.path.data.remote.response.CharactersDetailResponse
import com.path.data.remote.response.ComicsResponse
import com.path.data.repository.ApiRepository
import com.path.pathChallenge.BuildConfig
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CharacterDetailUseCase @Inject constructor(
    val apiRepository: ApiRepository
) {
    fun marvelCharacterDetailList(
        apiKey: String,
        characterId: String
    ): Observable<Resource<CharactersDetailResponse>> {
        return apiRepository.marvelCharacterDetailList(
            apiKey, BuildConfig.HASH_KEY, characterId
        ).subscribeOn(Schedulers.io())
    }

    fun marvelCharacterComics(
        apiKey: String,
        characterId: String
    ): Observable<Resource<ComicsResponse>> {
        return apiRepository.marvelCharacterComics(
            apiKey, BuildConfig.HASH_KEY, characterId
        ).subscribeOn(Schedulers.io())
    }
}
