package com.kovapss.gitmobile.view.repositories.detail.files.viewer

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.entities.repository.RepositoryFile

@InjectViewState
class FileViewerPresenter(private val file: RepositoryFile) : MvpPresenter<FileViewerView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showFile(file.url!!)
        viewState.showFileName(file.name)
    }

    fun openInBrowserItemClicked() { viewState.openFileInBrowser(file.htmlUrl) }
}