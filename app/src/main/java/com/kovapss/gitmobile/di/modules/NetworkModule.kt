package com.kovapss.gitmobile.di.modules

import com.kovapss.gitmobile.di.providers.AuthServiceProvider
import com.kovapss.gitmobile.di.providers.GistsServiceProvider
import com.kovapss.gitmobile.di.providers.HttpClientProvider
import com.kovapss.gitmobile.di.providers.UserServiceProvider
import com.kovapss.gitmobile.domain.GistsInteractor
import com.kovapss.gitmobile.domain.LaunchInteractor
import com.kovapss.gitmobile.entities.AuthData
import com.kovapss.gitmobile.domain.LoginInteractor
import com.kovapss.gitmobile.model.*
import com.kovapss.gitmobile.system.NetworkHelper
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import toothpick.config.Module


class NetworkModule : Module() {
    init {
        Logger.d("Network module init")

        bind(OkHttpClient::class.java).toProvider(HttpClientProvider::class.java)

        bind(AuthService::class.java).toProviderInstance(AuthServiceProvider(HttpClientProvider().get()))
                .providesSingletonInScope()

        bind(UserService::class.java).toProviderInstance(UserServiceProvider(HttpClientProvider().get()))
                .providesSingletonInScope()

        bind(GistsService::class.java).toProviderInstance(GistsServiceProvider(HttpClientProvider().get()))

        bind(AuthData::class.java).toInstance(AuthData(url = "https://github.com/login/oauth/authorize?",
                scopes = "scope=user,public_repo,repo&", clientId = "client_id=Iv1.3de123a4a157048d"))


        bind(NetworkHelper::class.java)

        bind(LoginInteractor::class.java)

        bind(LaunchInteractor::class.java)

        bind(AuthRepository::class.java)

        bind(UserRepository::class.java)

        bind(GistsRepository::class.java)

        bind(GistsInteractor::class.java)

    }
}