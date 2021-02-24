package com.mindorks.framework.mvi.data.repository

import com.mindorks.framework.mvi.data.api.ApiHelper
import com.mindorks.framework.mvi.data.model.User
import com.mindorks.framework.mvi.ui.main.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class MainRepository(private val apiHelper: ApiHelper) {

    private val _state = MutableStateFlow<MainState<List<User>>>(MainState.Idle)
    val state: StateFlow<MainState<List<User>>>
        get() = _state

    suspend fun fetchUser() {
        _state.value = MainState.Loading
        _state.value = try {
            MainState.Success(apiHelper.getUsers())
        } catch (e: Exception) {
            MainState.Error(e.localizedMessage)
        }
    }
}