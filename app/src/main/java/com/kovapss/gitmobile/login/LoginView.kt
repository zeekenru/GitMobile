package com.kovapss.gitmobile.login

import com.arellomobile.mvp.MvpView


interface LoginView : MvpView {
    fun loadUrl(url: String)

    fun showProgress()

    fun hideProgress()

    fun setOkResult()

}