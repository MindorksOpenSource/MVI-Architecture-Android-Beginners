package com.mindorks.framework.mvi.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mindorks.framework.mvi.data.model.User
import com.mindorks.framework.mvi.data.repository.MainRepository
import com.mindorks.framework.mvi.ui.main.dataholder.MainDataHolder
import com.mindorks.framework.mvi.ui.main.viewevent.MainEvent
import com.mindorks.framework.mvi.util.Resource
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _eventValue: MutableLiveData<MainEvent> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    val viewData: MutableLiveData<MainDataHolder> = MutableLiveData()
    val loadingValue: MutableLiveData<Boolean> = MutableLiveData()


    val dataValue: LiveData<Resource<MainDataHolder>> = Transformations
        .switchMap(_eventValue) { eventValue ->
            eventValue?.let {
                handleEvent(it)
            }
        }

    private fun handleEvent(event: MainEvent): LiveData<Resource<MainDataHolder>> {
        when (event) {
            is MainEvent.UsersLoadEvent -> {
                return mainRepository.getUsers(compositeDisposable)
            }

            //handle other events here
        }
    }

    fun loadUser(users: List<User>) {
        val state = MainDataHolder()
        state.users = users
        viewData.postValue(state)
    }

    fun loadingValue(value: Boolean) {
        loadingValue.postValue(value)
    }

    fun setEventValue(e: MainEvent) {
        val event: MainEvent = e
        _eventValue.postValue(event)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}