package com.kovapss.gitmobile.view.main

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.repository.Repository


interface MainView : MvpView {
    fun setUserData(avatarUrl : String, name : String)
    fun openUserProfileScreen(username : String)
    fun showLoginDialog()
    fun openLoginScreen()
    fun openCreateRepositoryScreen()
    fun openCreateGistScreen()
    fun openRepositoryDetailScreen(repository: Repository)
    fun openGistDetailScreen(gist: Gist)

}