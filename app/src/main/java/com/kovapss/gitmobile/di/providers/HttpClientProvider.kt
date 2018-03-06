package com.kovapss.gitmobile.di.providers

import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Provider

class HttpClientProvider : Provider<OkHttpClient>{

    override fun get() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger
        { message -> Logger.d(message) })
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return  OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

}
