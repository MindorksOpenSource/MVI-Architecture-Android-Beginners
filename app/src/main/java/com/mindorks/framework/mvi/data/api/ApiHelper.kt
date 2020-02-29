package com.mindorks.framework.mvi.data.api

import com.mindorks.framework.mvi.data.model.User
import retrofit2.Call


class ApiHelper(private val apiService: ApiService) {

    fun getUsers(): Call<List<User>> = apiService.getUsers()

}