package com.kovapss.gitmobile.di.providers

import com.google.gson.GsonBuilder
import com.kovapss.gitmobile.Constants
import com.kovapss.gitmobile.model.api.GistsService
import com.kovapss.gitmobile.model.api.RepositoriesService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider


class ReposServiceProvider(private val httpClient: OkHttpClient) : Provider<RepositoriesService> {

    override fun get(): RepositoriesService {
//        val gson = GsonBuilder()
//                .setDateFormat("YYYY-MM-DDThh:mm:ss")
//                .create()
        return Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(RepositoriesService::
                class.java)
    }

}