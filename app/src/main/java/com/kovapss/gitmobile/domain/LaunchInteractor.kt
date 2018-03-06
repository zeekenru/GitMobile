package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.model.repositories.UserRepository
import com.kovapss.gitmobile.model.system.PreferenceHelper
import com.orhanobut.logger.Logger
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LaunchInteractor {

    @Inject lateinit var preferenceHelper: PreferenceHelper

    @Inject lateinit var repository: UserRepository

    fun getUserCase(): Single<String> {
        if (preferenceHelper.getAccessToken() == PreferenceHelper.EMPTY_ACCESS_TOKEN) {
            Logger.d("Token is empty")
        }
        return repository.getCurrentUserFromNetwork(preferenceHelper.getAccessToken())
                .subscribeOn(Schedulers.io())
                .doOnSuccess({
                    Logger.d("USER : $it")
                })
                .map { it.login}
    }
}
