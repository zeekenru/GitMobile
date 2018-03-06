package com.kovapss.gitmobile.model.repositories

import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.entities.AccessData
import com.kovapss.gitmobile.model.api.AuthService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import toothpick.Toothpick
import javax.inject.Inject


class AuthRepository {

    @Inject lateinit var authService: AuthService

    init {
        Toothpick.inject(this, Toothpick.openScopes(NETWORK_SCOPE, APP_SCOPE))
    }

    fun auth(clientId: String,
             clientSecret: String,
             code: String): Observable<AccessData> {
        Logger.d("Making auth request")
        return authService.authorize(clientId, clientSecret, code)
    }

}