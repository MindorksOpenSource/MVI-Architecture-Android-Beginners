package com.mindorks.framework.mvi.data.repository

import com.mindorks.framework.mvi.data.api.ApiHelper
import com.mindorks.framework.mvi.data.model.User
import com.mindorks.framework.mvi.ui.main.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MainRepositoryImpl(private val apiHelper: ApiHelper): MainRepository {

    private val _state = MutableStateFlow<MainState<List<User>>>(MainState.Idle)
    override val state: StateFlow<MainState<List<User>>>
        get() = _state

    override suspend fun fetchUser() {
        _state.value = MainState.Loading
        _state.value = try {
            MainState.Success(apiHelper.getUsers())
        } catch (e: Exception) {
            MainState.Error(e.localizedMessage)
        }
    }
}