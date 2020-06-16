package com.mindorks.framework.mvi.ui.main.intent

sealed class MainIntent {

    object FetchUser : MainIntent()

}