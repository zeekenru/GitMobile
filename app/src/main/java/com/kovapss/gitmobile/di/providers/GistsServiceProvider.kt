package com.kovapss.gitmobile.di.providers

import com.kovapss.gitmobile.Constants
import com.kovapss.gitmobile.model.GistsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider


class GistsServiceProvider(private val httpClient: OkHttpClient) : Provider<GistsService> {
    override fun get(): GistsService {
        return Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(GistsService::class.java)
    }

}