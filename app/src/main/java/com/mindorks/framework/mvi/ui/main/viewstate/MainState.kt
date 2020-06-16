package com.mindorks.framework.mvi.ui.main.viewstate

import com.mindorks.framework.mvi.data.model.User

sealed class State {

    object Idle : State()
    object Loading : State()
    data class Users(val user: List<User>) : State()
    data class Error(val error: String) : State()

}