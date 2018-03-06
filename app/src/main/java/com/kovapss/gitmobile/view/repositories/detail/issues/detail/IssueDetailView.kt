package com.kovapss.gitmobile.view.repositories.detail.issues.detail

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.Comment
import com.kovapss.gitmobile.entities.issue.Issue


interface IssueDetailView : MvpView {
    fun showIssue(issue : Issue)
    fun openIssueInBrowser(htmlUrl : String)
    fun showComments(comments : List<Comment>)
    fun showNetworkError()
    fun hideNetworkError()
    fun showLoginDialog()
}