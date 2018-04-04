package com.kovapss.gitmobile.view.repositories.detail.files.edit

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.repository.UpdateRepositoryFileData


interface FileEditView : MvpView {
    fun setContent(fileName : String, content : String, branches : ArrayList<String>)
    fun setResult(data : UpdateRepositoryFileData)
}