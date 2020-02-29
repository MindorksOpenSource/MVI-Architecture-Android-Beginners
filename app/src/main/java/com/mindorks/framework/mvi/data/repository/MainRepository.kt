package com.mindorks.framework.mvi.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mindorks.framework.mvi.data.api.ApiHelper
import com.mindorks.framework.mvi.data.model.User
import com.mindorks.framework.mvi.ui.main.dataholder.MainDataHolder
import com.mindorks.framework.mvi.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(private val apiHelper: ApiHelper) {

    val TAG = "MainRepository"

    fun getUsers(): LiveData<Resource<MainDataHolder>> {
        val userLiveData = MutableLiveData<Resource<MainDataHolder>>()
        userLiveData.postValue(Resource.loading())
        apiHelper.getUsers().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                userLiveData.postValue(Resource.error())
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                userLiveData.postValue(Resource.success(MainDataHolder(response.body())))

            }
        })
        return userLiveData
    }

}