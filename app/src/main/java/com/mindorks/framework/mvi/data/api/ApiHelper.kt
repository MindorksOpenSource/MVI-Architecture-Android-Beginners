package com.mindorks.framework.mvi.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getUsers() = apiService.getUsers()

}