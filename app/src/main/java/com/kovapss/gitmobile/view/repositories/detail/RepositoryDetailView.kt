package com.kovapss.gitmobile.view.repositories.detail

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.repository.Readme
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.entities.repository.RepositoryStatus


interface RepositoryDetailView : MvpView {
    fun setRepositoryData(repository : Repository)
    fun showReadmeScreen(readme : Readme)
    fun showProgress()
    fun hideProgress()
    fun setRepositoryStatus(repositoryStatus: RepositoryStatus)
    fun showIssuesScreen(ownerLogin : String, repositoryName : String)
    fun showContentScreen(ownerLogin : String, repositoryName : String, dataType : Int)
    fun showRepositoryTopics(topics: List<String>)
    fun showFilesScreen(login: String, repositoryName : String)
    fun openUserScreen(username : String)
}