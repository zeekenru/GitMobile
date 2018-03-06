package com.kovapss.gitmobile.view.profile.content

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.repository.Repository


interface UserProfileContentView : MvpView {
    fun showData(data : List<*>)
    fun showProgress()
    fun hideProgress()
    fun showNetworkError()
    fun hideNetworkError()
    fun showEmptyResultError()
    fun hideEmptyResultError()
    fun openGistDetailScreen(gist: Gist)
    fun openRepositoryDetailScreen(repo: Repository)
}