package com.kovapss.gitmobile.view.repositories.detail.files.viewer

import com.arellomobile.mvp.MvpView


interface FileViewerView : MvpView {
    fun showFile(url : String)
    fun showFileName(name : String)
    fun openFileInBrowser(htmlUrl: String)
}