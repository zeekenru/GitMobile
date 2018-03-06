package com.kovapss.gitmobile.view.repositories.detail.readme

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.entities.repository.Readme


@InjectViewState
class RepositoryReadmePresenter(private val readme: Readme) : MvpPresenter<RepositoryReadmeView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showReadme(readme)
    }
}