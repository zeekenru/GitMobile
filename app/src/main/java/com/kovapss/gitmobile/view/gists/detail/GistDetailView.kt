package com.kovapss.gitmobile.view.gists.detail

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.Comment


interface GistDetailView : MvpView {

    fun showGist(gist : Gist, showLineNumbers : Boolean)

    fun showComments(comments: List<Comment>)

    fun setGistStarredStatus(isStarred : Boolean)

    fun showLoginDialog()

    fun showInternetError()

    fun showUndefinedError(msg : String)

    fun showProgress()

    fun hideProgress()

    fun hideComments()

    fun openGistInBrowser(htmlUrl : String)

}