package com.kovapss.gitmobile.model.interceptors

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.model.system.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import toothpick.Toothpick
import javax.inject.Inject


class AuthInterceptor : Interceptor {

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (preferenceHelper.getAuthStatus()) {
            chain.proceed(chain.request().newBuilder()
                    .addHeader("Authorization",
                            "token ${preferenceHelper.getAccessToken()}")
                    .build())
        } else {
            chain.proceed(chain.request())
        }

    }
}