package com.kovapss.gitmobile.gists.create

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.system.ResourceHelper
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class CreateGistPresenter : MvpPresenter<CreateGistView>() {

    @Inject lateinit var resourseHelper : ResourceHelper

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun clickOnAddFileBtn() {
        viewState.showFileChooseDialog()
    }

    fun selectedItem(itemIndex: Int) {
        when (itemIndex){
            0 -> viewState.showCreateFileDialog()
            1 -> viewState.getFileFromSystem()
        }
    }

    fun onClickDoneMenuItem() {
        viewState.showGistVisibilityDialog()
    }


    fun fileCreated(resultCode: Int, file : GistFile) {

    }

    fun onFileClick(file : GistFile){

    }

    fun onDeleteFileClick(file: GistFile) {

    }

}