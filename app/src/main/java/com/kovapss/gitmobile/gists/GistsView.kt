package com.kovapss.gitmobile.gists

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.Gist


interface GistsView : MvpView {

    fun showInternetError()

    fun showUndefinedError(msg : String)

    fun showProgress()

    fun hideProgress()

    fun showGists(gists : List<Gist>)

    fun openGistCreateView()

}