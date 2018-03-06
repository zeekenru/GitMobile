package com.kovapss.gitmobile.view.profile.overview

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.User
import toothpick.Toothpick

@InjectViewState
class ProfileOverviewPresenter(private val user : User) : MvpPresenter<UserProfileOverviewView>() {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
        viewState.setUserData(user)
    }
}