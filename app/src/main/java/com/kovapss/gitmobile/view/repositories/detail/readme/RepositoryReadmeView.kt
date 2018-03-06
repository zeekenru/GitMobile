package com.kovapss.gitmobile.view.repositories.detail.readme

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.repository.Readme


interface RepositoryReadmeView : MvpView {
    fun showReadme(readme : Readme)
}