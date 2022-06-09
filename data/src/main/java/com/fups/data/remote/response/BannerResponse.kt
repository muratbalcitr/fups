package com.fups.data.remote.response

data class BannerResponse(
    val id: Int,
    val title: String,
    var description: String,
    var image: Int
)