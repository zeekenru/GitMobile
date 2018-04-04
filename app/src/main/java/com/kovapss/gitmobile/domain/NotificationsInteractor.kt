package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.Notification
import com.kovapss.gitmobile.model.repositories.NotificationsRepository
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject


class NotificationsInteractor {

    @Inject lateinit var repository : NotificationsRepository

    @Inject lateinit var preferenceHelper: PreferenceHelper

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun getNotifications(getAll : Boolean)  : Single<List<Notification>> =
            repository.getNotifications(getAll)
                    .map { it.body() }
                    .subscribeOn(Schedulers.io())
}