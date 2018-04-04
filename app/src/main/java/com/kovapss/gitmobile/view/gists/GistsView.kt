package com.kovapss.gitmobile.view.gists

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.Gist


interface GistsView : MvpView {

    fun showInternetError()

    fun showUndefinedError()

    fun showNotAuthError()

    fun showEmptyResultError()

    fun showProgress()

    fun hideProgress()

    fun showGists(gists : List<Gist>)

    fun addGists(gists: List<Gist>)

    fun openGistCreateView()

    fun openDetailGist(gist: Gist)

    fun openLoginDialogScreen()

    fun setFabVisible(isVisible: Boolean)

}