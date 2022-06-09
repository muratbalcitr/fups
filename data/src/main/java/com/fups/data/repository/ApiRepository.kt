package com.fups.data.repository

import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiDataSource: ApiDataSource
)