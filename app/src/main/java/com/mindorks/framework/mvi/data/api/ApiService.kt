package com.mindorks.framework.mvi.data.api

import com.mindorks.framework.mvi.data.model.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("5e5972ac2f0000d0989626ba")
    fun getUsers(): Call<List<User>>

}