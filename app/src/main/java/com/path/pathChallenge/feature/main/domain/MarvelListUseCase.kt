package com.path.pathChallenge.feature.main.domain

import com.path.data.Resource
import com.path.data.remote.response.CharactersResponse
import com.path.data.repository.ApiRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MarvelListUseCase @Inject constructor(
    val apiRepository: ApiRepository
) {
    fun fetchMarvelList(
        apiKey: String,
        hash: String,
        limit: Int? = 30,
        offset: Int?=null,
        orderBy: String? = null
    ): Observable<Resource<CharactersResponse>> {
        return apiRepository.fetchMarvelList(
            apiKey,hash, limit, offset, orderBy
        ).subscribeOn(Schedulers.io())
    }
}
