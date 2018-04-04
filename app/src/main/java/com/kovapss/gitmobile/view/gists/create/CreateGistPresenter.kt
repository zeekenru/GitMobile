package com.kovapss.gitmobile.view.gists.create

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.orhanobut.logger.Logger
import toothpick.Toothpick

@InjectViewState
class CreateGistPresenter : MvpPresenter<CreateGistView>() {


    fun clickOnAddFileBtn() {
        viewState.showCreateFileScreen()
    }

    fun onClickDoneMenuItem() {

    }


    fun fileCreated(file : GistFile) {
        viewState.addFile(file)
    }

    fun onFileClick(file : GistFile){

    }

    fun onDeleteFileClick(position : Int) {
        viewState.deleteItem(position)
    }

}