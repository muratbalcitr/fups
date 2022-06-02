package com.path.pathChallenge.feature.main.domain

import com.path.data.remote.response.CharactersResponse

sealed class MarvelListViewEvent {
    data class SelectMarvel(val item: CharactersResponse.Data.Result) : MarvelListViewEvent()
}
