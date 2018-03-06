package com.kovapss.gitmobile.view.profile.overview

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.User


interface UserProfileOverviewView : MvpView {
    fun setUserData(user: User)
}