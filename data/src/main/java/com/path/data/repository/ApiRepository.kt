package com.path.data.repository

import com.path.data.Resource
import com.path.data.remote.response.CharactersDetailResponse
import com.path.data.remote.response.CharactersResponse
import com.path.data.remote.response.ComicsResponse
import com.path.data.utils.toObservable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiDataSource: ApiDataSource
) {
    fun fetchMarvelList(
        apiKey: String,
        hash: String,
        limit: Int? = 30,
        offset: Int? = null,
        orderBy: String? = null
    ): Observable<Resource<CharactersResponse>> {
        return Observable.create { emitter ->
            apiDataSource.marvelCharacterList(apiKey, hash, limit, offset, orderBy.toString())
                .toObservable(emitter)
        }
    }

    fun marvelCharacterDetailList(
        apiKey: String,
        hash: String,
        charactersId: String
    ): Observable<Resource<CharactersDetailResponse>> {
        return Observable.create { emitter ->
            apiDataSource.marvelCharacterDetailList(apiKey, hash, charactersId)
                .toObservable(emitter)
        }
    }

    fun marvelCharacterComics(
        apiKey: String,
        hash: String,
        charactersId: String
    ): Observable<Resource<ComicsResponse>> {
        return Observable.create { emitter ->
            apiDataSource.marvelCharacterComics(apiKey, hash, charactersId)
                .toObservable(emitter)
        }
    }
}
