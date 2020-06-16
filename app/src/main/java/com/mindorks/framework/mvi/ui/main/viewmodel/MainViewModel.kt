package com.mindorks.framework.mvi.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvi.data.repository.MainRepository
import com.mindorks.framework.mvi.ui.main.viewstate.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State>
        get() = _state

    init {
        viewModelScope.launch {
            _state.value = State.Loading
            _state.value = try {
                State.Users(repository.getUsers())
            } catch (e: Exception) {
                State.Error(e.localizedMessage)
            }
        }
    }

}