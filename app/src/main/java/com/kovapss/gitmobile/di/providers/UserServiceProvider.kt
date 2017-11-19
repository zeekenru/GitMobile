package com.kovapss.gitmobile.di.providers

import com.kovapss.gitmobile.Constants
import com.kovapss.gitmobile.model.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider


class UserServiceProvider(private val okHttpClient: OkHttpClient) : Provider<UserService> {
    override fun get(): UserService {
        return Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(UserService::class.java)
    }

}