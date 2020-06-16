package com.mindorks.framework.mvi.data.repository

import com.mindorks.framework.mvi.data.api.ApiHelper


class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}