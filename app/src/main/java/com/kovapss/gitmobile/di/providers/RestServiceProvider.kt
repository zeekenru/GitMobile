package com.kovapss.gitmobile.di.providers

import com.google.gson.GsonBuilder
import com.kovapss.gitmobile.Constants
import com.kovapss.gitmobile.model.interceptors.AuthInterceptor
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Provider


class RestServiceProvider : Provider<Retrofit> {

    private var httpClient : OkHttpClient? = null

    override fun get(): Retrofit {
        if (httpClient == null){
            val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger
            { message -> Logger.d(message) })
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            httpClient =  OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor { chain -> chain.proceed(chain.request().newBuilder()
                            .addHeader("User-Agent", "GitMobile").build())
                    }
                    .addInterceptor(AuthInterceptor())
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build()
        }
//        val gson = GsonBuilder()
//                .setLenient()
//                .create()
        return Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()


    }
}