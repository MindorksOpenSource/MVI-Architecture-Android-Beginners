package com.mindorks.framework.mvi.data.api

import com.mindorks.framework.mvi.data.model.User

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}