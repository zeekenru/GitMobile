package com.kovapss.gitmobile.di.providers

import com.kovapss.gitmobile.model.interceptors.AuthInterceptor
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Provider

class HttpClientProvider : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger
        { message -> Logger.d(message) })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor { chain -> chain.proceed(chain.request().newBuilder()
                            .addHeader("User-Agent", "GitMobile").build())
                }
                .addInterceptor(AuthInterceptor())
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build()
    }

}
