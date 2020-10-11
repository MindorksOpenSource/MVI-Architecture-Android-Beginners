package com.mindorks.framework.mvi.data.api

import com.mindorks.framework.mvi.data.model.User
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}