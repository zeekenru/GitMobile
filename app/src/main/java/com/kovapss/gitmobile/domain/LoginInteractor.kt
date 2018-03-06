package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.model.repositories.AuthRepository
import com.kovapss.gitmobile.Constants
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.AuthData
import com.kovapss.gitmobile.model.repositories.UserRepository
import com.kovapss.gitmobile.model.system.PreferenceHelper
import com.orhanobut.logger.Logger
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject


class LoginInteractor {

    @Inject lateinit var authRepository: AuthRepository

    @Inject lateinit var preferenceHelper: PreferenceHelper

    @Inject lateinit var authData: AuthData

    @Inject lateinit var userRepository: UserRepository

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.NETWORK_SCOPE, Scopes.APP_SCOPE))
    }

    fun getAuthUrlCase(): String = with(authData) { url + scopes + clientId }


    fun authUserCase(code: String): Completable {
        val observable = authRepository.auth(Constants.CLIENT_ID, Constants.CLIENT_SECRET, code)
                .subscribeOn(Schedulers.io())
                .map { it.token }
                .doOnNext{
                    Logger.d("Токен получен : $it")
                    preferenceHelper.setAccessToken(it)
                    preferenceHelper.setAuthStatus(true)
                }
                .flatMap {userRepository.getCurrentUserFromNetwork(it).toObservable() }
                .doOnNext{preferenceHelper.setCurrentUserLogin(it.login)}
        return Completable.fromObservable(observable)

    }

}