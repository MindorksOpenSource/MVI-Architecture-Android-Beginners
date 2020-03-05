package com.mindorks.framework.mvi.ui.main.viewevent

sealed class MainEvent {
    object UsersLoadEvent : MainEvent()
}