package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.model.AuthRepository
import com.kovapss.gitmobile.Constants
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.AuthData
import com.kovapss.gitmobile.system.PreferenceManager
import com.orhanobut.logger.Logger
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject


class LoginInteractor {

    @Inject lateinit var repository: AuthRepository

    @Inject lateinit var preferenceManager: PreferenceManager

    @Inject lateinit var authData: AuthData

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.NETWORK_SCOPE, Scopes.APP_SCOPE))
    }

    fun getAuthUrlCase(): String = with(authData) { url + scopes + clientId }


    fun authUserCase(code: String): Completable {
        val observable = repository.auth(Constants.CLIENT_ID, Constants.CLIENT_SECRET, code)
                .subscribeOn(Schedulers.io())
                .doOnNext {
                    with(it) {
                        Logger.d("Токен получен : $token")
                        preferenceManager.setAccessToken(token)
                    }
                }
        return Completable.fromObservable(observable)

    }

}