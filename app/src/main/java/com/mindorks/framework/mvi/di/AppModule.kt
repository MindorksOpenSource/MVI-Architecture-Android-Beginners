package com.mindorks.framework.mvi.di

import com.mindorks.framework.mvi.data.api.ApiHelper
import com.mindorks.framework.mvi.data.api.ApiHelperImpl
import com.mindorks.framework.mvi.util.ContextProvider
import com.mindorks.framework.mvi.util.CoroutineContextProvider
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    internal abstract fun bindCoroutineContextProvider(contextProvider: CoroutineContextProvider): ContextProvider

    @Binds
    internal abstract fun bindApiHelper(apiHelper: ApiHelperImpl): ApiHelper
}