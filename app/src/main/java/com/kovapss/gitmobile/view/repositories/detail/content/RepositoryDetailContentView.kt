package com.kovapss.gitmobile.view.repositories.detail.content

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.DelegatesAdapterModel


interface RepositoryDetailContentView : MvpView {
    fun showProgress()
    fun hideProgress()
    fun showNetworkError()
    fun hideNetworkError()
    fun showEmptyResultError()
    fun hideEmptyResultError()
    fun showData(data : List<DelegatesAdapterModel>)
    fun openUserDetailScreen(username : String)
}