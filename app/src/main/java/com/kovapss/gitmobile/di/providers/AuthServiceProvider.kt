package com.kovapss.gitmobile.di.providers

import com.kovapss.gitmobile.model.api.AuthService
import com.kovapss.gitmobile.Constants.OAUTH_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider


class AuthServiceProvider(private val httpClient: OkHttpClient) : Provider<AuthService> {

    override fun get(): AuthService {
        return Retrofit.Builder()
                .baseUrl(OAUTH_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(AuthService::class.java)
    }


}