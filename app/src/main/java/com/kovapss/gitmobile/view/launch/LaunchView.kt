package com.kovapss.gitmobile.view.launch

import com.arellomobile.mvp.MvpView


interface LaunchView : MvpView {

    fun showInternetError()

    fun showUndefinedError()

    fun showProgress()

    fun hideProgress()

    fun startMainActivity()

    fun startLoginActivity()

    fun showWelcome(userName : String)
}