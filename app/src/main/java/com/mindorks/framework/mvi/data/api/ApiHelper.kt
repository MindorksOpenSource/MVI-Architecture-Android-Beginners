package com.mindorks.framework.mvi.data.api

import com.mindorks.framework.mvi.data.model.User

interface ApiHelper {

    suspend fun getUsers(): List<User>

}