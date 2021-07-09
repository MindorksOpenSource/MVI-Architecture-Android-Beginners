package com.mindorks.framework.mvi.di

import com.mindorks.framework.mvi.ui.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}