package com.kovapss.gitmobile.view.repositories.detail.files.edit

import android.util.Base64
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.entities.repository.RepositoryFile
import com.kovapss.gitmobile.entities.repository.UpdateRepositoryFileData
import com.orhanobut.logger.Logger

@InjectViewState
class RepositoryFileEditPresenter(val file: RepositoryFile, val branches : ArrayList<String>) : MvpPresenter<FileEditView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setContent(file.name, fromBase64(file.content!!), branches)
    }


    private fun fromBase64(encodedString: String) = String(Base64.decode(encodedString, Base64.DEFAULT))


    fun doneClicked(content: String, commitMessage: String, selectedBranchPosition : Int) = with(file) {
        viewState.setResult(UpdateRepositoryFileData(path, commitMessage, content, sha, branches[selectedBranchPosition]))
    }

}