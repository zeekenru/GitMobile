package com.kovapss.gitmobile.view.repositories.detail.files

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.repository.RepositoryFile


interface RepositoryFilesView : MvpView {
    fun setBranches(branches: List<String>)
    fun showRepoContent(data: List<RepositoryFile>)
    fun showProgress()
    fun hideProgress()
    fun showNetworkError()
    fun hideNetworkError()
    fun openFileViewer(file: RepositoryFile)
    fun openCreateFileScreen(branches: List<String>)
}