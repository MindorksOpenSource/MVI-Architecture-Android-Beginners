package com.mindorks.framework.mvi.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mindorks.framework.mvi.data.repository.MainRepository
import com.mindorks.framework.mvi.ui.main.dataholder.MainDataHolder
import com.mindorks.framework.mvi.ui.main.viewevent.MainEvents
import com.mindorks.framework.mvi.util.Resource
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _eventValue: MutableLiveData<MainEvents> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    val dataOutput: LiveData<Resource<MainDataHolder>> = Transformations
        .switchMap(_eventValue) { eventValue ->
            eventValue?.let {
                handleEvent(it)
            }
        }

    private fun handleEvent(event: MainEvents): LiveData<Resource<MainDataHolder>> {
        when (event) {
            is MainEvents.UsersLoadEvent -> {
                return mainRepository.getUsers(compositeDisposable)
            }
        }
    }

    fun setEventValue(event: MainEvents) {
        _eventValue.postValue(event)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}