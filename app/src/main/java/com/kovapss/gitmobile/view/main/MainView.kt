package com.kovapss.gitmobile.view.main

import com.arellomobile.mvp.MvpView


interface MainView : MvpView {
    fun setUserData(avatarUrl : String, name : String)
    fun openUserProfileScreen(username : String)
    fun showLoginDialog()
    fun openLoginScreen()
}