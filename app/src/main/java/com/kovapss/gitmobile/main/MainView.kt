package com.kovapss.gitmobile.main

import com.arellomobile.mvp.MvpView


interface MainView : MvpView {
    fun setUserData(avatarUrl : String, name : String)
}