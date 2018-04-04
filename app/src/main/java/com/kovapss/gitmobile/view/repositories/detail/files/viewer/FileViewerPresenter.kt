package com.kovapss.gitmobile.view.repositories.detail.files.viewer

import android.util.Base64
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.entities.repository.RepositoryFile
import java.util.*

@InjectViewState
class FileViewerPresenter(private val file: RepositoryFile) : MvpPresenter<FileViewerView>() {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showFile(fromBase64(file.content!!))
        viewState.showFileName(file.name)
    }

    fun openInBrowserItemClicked() { viewState.openFileInBrowser(file.htmlUrl) }

    private fun fromBase64(encodedString : String) = String(Base64.decode(encodedString, Base64.DEFAULT))

    fun editFileItemClicked() = viewState.openEditScreen(file)

}