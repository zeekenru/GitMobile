package com.kovapss.gitmobile.gists.create

import com.arellomobile.mvp.MvpView


interface CreateGistView : MvpView {
    fun showFileChooseDialog()
    fun showGistVisibilityDialog()
    fun showCreateFileDialog()
    fun getFileFromSystem()
    fun showFiles(files : List<GistFile>)

}