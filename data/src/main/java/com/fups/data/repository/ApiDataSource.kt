package com.fups.data.repository

import com.fups.data.remote.ProjectService
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val projectService: ProjectService
) {


}
