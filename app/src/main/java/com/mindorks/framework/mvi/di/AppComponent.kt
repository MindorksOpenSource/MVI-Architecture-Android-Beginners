package com.mindorks.framework.mvi.di

import android.app.Application
import com.mindorks.framework.mvi.MVIApplication
import com.mindorks.framework.mvi.data.api.RetrofitBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ActivityBindingModule::class,
        AndroidSupportInjectionModule::class,
        RetrofitBuilder::class,
        AppModule::class]
)
interface AppComponent : AndroidInjector<MVIApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}