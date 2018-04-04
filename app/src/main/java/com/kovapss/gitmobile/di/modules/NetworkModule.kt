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
import retrofit2.Retrofit
import toothpick.config.Module


class NetworkModule : Module() {
    init {
        Logger.d("Network module init")

        bind(AuthService::class.java)
                .toProviderInstance(AuthServiceProvider(HttpClientProvider().get()))
                .providesSingletonInScope()

        bind(UserService::class.java)
                .toInstance(RestServiceProvider().get().create(UserService::class.java))

        bind(GistsService::class.java)
                .toInstance(RestServiceProvider().get().create(GistsService::class.java))

        bind(RepositoriesService::class.java)
                .toInstance(RestServiceProvider().get().create(RepositoriesService::class.java))

        bind(SearchService::class.java)
                .toInstance(RestServiceProvider().get().create(SearchService::class.java))

        bind(NotificationService::class.java)
                .toInstance(RestServiceProvider().get().create(NotificationService::class.java))

        bind(AuthData::class.java).toInstance(AuthData("https://github.com/login/oauth/authorize",
                "user,repo,notifications,gist", "7afe70c76de1a654c60e"))

        bind(NetworkHelper::class.java).to(NetworkHelperImpl::class.java)

        bind(LoginInteractor::class.java)

        bind(MainInteractor::class.java)

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

        bind(NotificationsInteractor::class.java)

        bind(NotificationsRepository::class.java)

    }
}