package com.mindorks.framework.mvi.data.repository

import com.mindorks.framework.mvi.data.model.User
import com.mindorks.framework.mvi.ui.main.viewstate.MainState
import kotlinx.coroutines.flow.StateFlow
import java.util.*

interface MainRepository {

    val state: StateFlow<MainState<List<User>>>

    suspend fun fetchUser()
}