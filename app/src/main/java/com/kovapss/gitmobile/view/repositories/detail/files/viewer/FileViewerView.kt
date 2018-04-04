package com.kovapss.gitmobile.view.repositories.detail.files.viewer

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.repository.RepositoryFile


interface FileViewerView : MvpView {
    fun showFile(code : String)
    fun showFileName(name : String)
    fun openFileInBrowser(htmlUrl: String)
    fun openEditScreen(file : RepositoryFile)
}