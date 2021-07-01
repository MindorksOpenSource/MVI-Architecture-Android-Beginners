package com.mindorks.framework.mvi.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvi.data.model.User
import com.mindorks.framework.mvi.data.repository.MainRepository
import com.mindorks.framework.mvi.ui.main.intent.MainIntent
import com.mindorks.framework.mvi.ui.main.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = repository.state
    val state: StateFlow<MainState<List<User>>>
        get() = _state

    init {
        viewModelScope.launch {
            userIntent.send(MainIntent.FetchUser)
            handleIntent()
        }
    }

    private suspend fun handleIntent() {
        userIntent.consumeAsFlow().collect {
            when (it) {
                is MainIntent.FetchUser -> repository.fetchUser()
            }
        }
    }
}