package com.kovapss.gitmobile.model.repositories

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.model.api.NotificationService
import toothpick.Toothpick
import javax.inject.Inject


class NotificationsRepository {

    @Inject lateinit var api : NotificationService

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun getNotifications( getAll : Boolean) = api.getNotifications(getAll)
}