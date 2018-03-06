package com.kovapss.gitmobile.view.repositories

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.repository.Repository


interface RepositoriesView : MvpView {
    fun showData(data : List<Repository>)
    fun showProgress()
    fun hideProgress()
    fun showNetworkError()
    fun hideNetworkError()
    fun showEmptyResultError()
    fun hideEmptyResultError()
    fun openRepositoryDetailScreen(repo: Repository)
}