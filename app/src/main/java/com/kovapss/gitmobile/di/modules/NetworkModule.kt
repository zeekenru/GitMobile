package com.kovapss.gitmobile.di.modules

import com.kovapss.gitmobile.di.providers.*
import com.kovapss.gitmobile.domain.*
import com.kovapss.gitmobile.entities.AuthData
import com.kovapss.gitmobile.model.api.*
import com.kovapss.gitmobile.model.repositories.*
import com.kovapss.gitmobile.model.system.NetworkHelper
import com.kovapss.gitmobile.model.system.NetworkHelperImpl
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import toothpick.config.Module


class NetworkModule : Module() {
    init {
        Logger.d("Network module init")

        bind(OkHttpClient::class.java).toProvider(HttpClientProvider::class.java)

        bind(AuthService::class.java)
                .toProviderInstance(AuthServiceProvider(HttpClientProvider().get()))
                .providesSingletonInScope()

        bind(UserService::class.java)
                .toProviderInstance(UserServiceProvider(HttpClientProvider().get()))
                .providesSingletonInScope()

        bind(GistsService::class.java)
                .toProviderInstance(GistsServiceProvider(HttpClientProvider().get()))

        bind(RepositoriesService::class.java)
                .toProviderInstance(ReposServiceProvider((HttpClientProvider().get())))
                .providesSingletonInScope()
        bind(SearchService::class.java)
                .toProviderInstance(SearchServiceProvider((HttpClientProvider().get())))


        bind(AuthData::class.java).toInstance(AuthData(url = "https://github.com/login/oauth/authorize?",
                scopes = "scope=user,public_repo,repo&", clientId = "client_id=Iv1.3de123a4a157048d"))

        bind(NetworkHelper::class.java).to(NetworkHelperImpl::class.java)

        bind(LoginInteractor::class.java)

        bind(LaunchInteractor::class.java)

        bind(SearchInteractor::class.java)

        bind(AuthRepository::class.java)

        bind(UserRepository::class.java)

        bind(GistsRepository::class.java)

        bind(GistsInteractor::class.java)

        bind(GistDetailInteractor::class.java)

        bind(RepositoriesInteractor::class.java)

        bind(UserProfileInteractor::class.java)

        bind(RepositoryDetailInteractor::class.java)

        bind(ReposRepository::class.java)

        bind(SearchRepository::class.java)

    }
}