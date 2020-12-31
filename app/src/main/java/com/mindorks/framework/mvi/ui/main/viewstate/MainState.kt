package com.mindorks.framework.mvi.ui.main.viewstate

sealed class MainState<out R> {
    object Idle : MainState<Nothing>()
    object Loading : MainState<Nothing>()
    data class Success<out T>(val data: T?) : MainState<T>()
    data class Error<out T>(val message: String?) : MainState<T>()
}