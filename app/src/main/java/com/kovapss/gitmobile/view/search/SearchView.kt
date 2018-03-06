package com.kovapss.gitmobile.view.search

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.DelegatesAdapterModel
import com.kovapss.gitmobile.entities.repository.Repository


interface SearchView : MvpView {
    fun showProgress()
    fun hideProgress()
    fun showSearchResults(results : List<DelegatesAdapterModel>)
    fun openRepositoryDetailScreen(repository: Repository)
    fun openUserProfileScreen(username : String)
}