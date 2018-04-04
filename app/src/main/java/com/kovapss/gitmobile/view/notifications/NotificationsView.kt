package com.kovapss.gitmobile.view.notifications

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.Notification


interface NotificationsView : MvpView {
    fun showNotifications(notifications: List<Notification>)
    fun showProgress()
    fun hideProgress()
    fun showNetworkError()
    fun hideNetworkError()
    fun showEmptyResultError()
    fun hideEmptyResultError()
}