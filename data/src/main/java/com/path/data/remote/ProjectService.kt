package com.path.data.remote

import com.path.data.remote.response.CharactersDetailResponse
import com.path.data.remote.response.CharactersResponse
import com.path.data.remote.response.ComicsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectService {

    @GET(Marvels.characters)
    fun marvelCharacterList(
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("orderBy") orderBy: String = "name",
         @Query("ts") ts: String = "1",
    ): Single<CharactersResponse>

    @GET(Marvels.characterDetail)
    fun marvelCharacterDetail(
        @Path("characterId") characterId: String? = null,
        @Query("apikey") apikey: String? = null,
        @Query("hash") hash: String,
        @Query("ts") ts: String = "1",
    ): Single<CharactersDetailResponse>

    @GET(Marvels.characterComics)
    fun marvelCharacterComics(
        @Path("characterId") characterId: String? = null,
        @Query("apikey") apikey: String? = null,
        @Query("hash") hash: String,
        @Query("ts") ts: String = "1",
        @Query("startYear") dateRange: String = "2005",
        @Query("limit") limit: Int = 10,
        @Query("orderBy") orderBy: String = "modified",
    ): Single<ComicsResponse>

    object Marvels {
        const val characters = "/v1/public/characters"
        const val characterDetail = "/v1/public/characters/{characterId}"
        const val characterComics = "/v1/public/characters/{characterId}/comics"
    }
}
