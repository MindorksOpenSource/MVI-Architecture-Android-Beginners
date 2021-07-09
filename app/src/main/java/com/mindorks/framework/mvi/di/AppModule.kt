package com.mindorks.framework.mvi.di

import com.mindorks.framework.mvi.data.api.ApiHelper
import com.mindorks.framework.mvi.data.api.ApiHelperImpl
import com.mindorks.framework.mvi.data.repository.MainRepository
import com.mindorks.framework.mvi.data.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    internal abstract fun bindApiHelper(apiHelper: ApiHelperImpl): ApiHelper

    @Binds
    internal abstract fun bindViewModel(repository: MainRepositoryImpl): MainRepository

}