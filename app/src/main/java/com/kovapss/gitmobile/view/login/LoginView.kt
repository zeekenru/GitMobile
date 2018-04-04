package com.kovapss.gitmobile.view.login

import com.arellomobile.mvp.MvpView


interface LoginView : MvpView {
    fun loadUrl(url: String)

    fun showProgress()

    fun hideProgress()

    fun setOkResult()

    fun hideKeyboard()

}