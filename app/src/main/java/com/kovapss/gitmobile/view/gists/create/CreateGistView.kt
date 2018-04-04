package com.kovapss.gitmobile.view.gists.create

import com.arellomobile.mvp.MvpView


interface CreateGistView : MvpView {
    fun showCreateFileScreen()
    fun addFile(file: GistFile)
    fun deleteItem(position: Int)

}