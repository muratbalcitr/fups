package com.path.data.repository

import com.path.data.remote.ProjectService
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val projectService: ProjectService
) {

    fun marvelCharacterList(
        apiKey: String,
        hash: String,
        limit: Int? = 30,
        offset: Int? = null,
        orderBy: String? = null
    ) =
        projectService.marvelCharacterList(apiKey, hash, limit, offset, orderBy.toString())

    fun marvelCharacterDetailList(
        apiKey: String,
        hash: String,
        charactersId: String
    ) =
        projectService.marvelCharacterDetail(charactersId, apiKey, hash)
    fun marvelCharacterComics(
        apiKey: String,
        hash: String,
        charactersId: String
    ) =
        projectService.marvelCharacterComics(charactersId, apiKey, hash)
}
