package com.kovapss.gitmobile.view.repositories.detail.collaborators

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.User


interface AddCollaboratorView : MvpView {
    fun showUsers(usernames : List<String>)
}