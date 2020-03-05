package com.mindorks.framework.mvi.data.api

import com.mindorks.framework.mvi.data.model.User
import io.reactivex.Single

interface ApiService {

    fun getUsers(): Single<List<User>>

}