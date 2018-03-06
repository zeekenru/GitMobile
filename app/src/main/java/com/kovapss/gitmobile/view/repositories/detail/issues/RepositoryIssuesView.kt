package com.kovapss.gitmobile.view.repositories.detail.issues

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.issue.Issue


interface RepositoryIssuesView : MvpView {
    fun showData(data : List<Issue>)
    fun showProgress()
    fun hideProgress()
    fun showNetworkError()
    fun hideNetworkError()
    fun showEmptyResultError()
    fun hideEmptyResultError()
    fun openIssueDetailScreen(issue : Issue, ownerLogin : String, repositoryName : String)
}