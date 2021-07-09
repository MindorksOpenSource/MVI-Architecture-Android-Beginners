package com.mindorks.framework.mvi.data.repository

import com.mindorks.framework.mvi.data.model.User
import com.mindorks.framework.mvi.ui.main.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
interface MainRepository {

    val state: StateFlow<MainState<List<User>>>

    suspend fun fetchUser()
}