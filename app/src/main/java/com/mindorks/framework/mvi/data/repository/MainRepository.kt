package com.mindorks.framework.mvi.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mindorks.framework.mvi.data.api.ApiHelper
import com.mindorks.framework.mvi.ui.main.dataholder.MainDataHolder
import com.mindorks.framework.mvi.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers(compositeDisposable: CompositeDisposable): LiveData<Resource<MainDataHolder>> {
        val userLiveData = MutableLiveData<Resource<MainDataHolder>>()
        userLiveData.postValue(Resource.loading(null))
        compositeDisposable.add(
            apiHelper.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    userLiveData.postValue(Resource.success(MainDataHolder(userList)))
                }, {
                    userLiveData.postValue(Resource.error("Something Went Wrong", null))
                })
        )
        return userLiveData
    }

}