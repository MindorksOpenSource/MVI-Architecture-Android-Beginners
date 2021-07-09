package com.mindorks.framework.mvi.data.api

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class RetrofitBuilder {

    @Singleton
    @Provides
    fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}

private const val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"