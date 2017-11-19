package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.model.UserRepository
import com.kovapss.gitmobile.system.PreferenceManager
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LaunchInteractor {

    @Inject lateinit var preferenceManager: PreferenceManager

    @Inject lateinit var repository: UserRepository

    fun getUserCase(): Observable<String> {
        if (preferenceManager.getAccessToken() == PreferenceManager.EMPTY_ACCESS_TOKEN) {
            Logger.d("Token is empty")
        }
        return repository.getCurrentUserFromNetwork(preferenceManager.getAccessToken())
                .subscribeOn(Schedulers.io())
                .doOnNext({
                    Logger.d("USER : $it")
                })
                .map { it.name }
    }
}
