package com.mindorks.framework.mvi.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mindorks.framework.mvi.data.api.ApiHelper
import com.mindorks.framework.mvi.data.repository.MainRepository
import com.mindorks.framework.mvi.ui.main.viewmodel.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val apiHelper: ApiHelper,
    private val contextProvider: ContextProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper), contextProvider) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}