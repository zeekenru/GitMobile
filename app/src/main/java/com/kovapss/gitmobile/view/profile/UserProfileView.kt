package com.kovapss.gitmobile.view.profile

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.User


interface UserProfileView : MvpView {
    fun setUserLogin(login : String)
    fun showOverviewScreen(user : User)
    fun showProfileContentScreen(dataType : Int, username: String)
    fun showProgress()
    fun hideProgress()
}