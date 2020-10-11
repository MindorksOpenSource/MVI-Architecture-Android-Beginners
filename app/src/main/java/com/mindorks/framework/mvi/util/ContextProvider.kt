package com.mindorks.framework.mvi.util

import kotlin.coroutines.CoroutineContext

interface ContextProvider {
    val main: CoroutineContext
    val io: CoroutineContext
}