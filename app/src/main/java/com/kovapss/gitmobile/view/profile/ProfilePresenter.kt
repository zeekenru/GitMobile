package com.kovapss.gitmobile.view.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.UserProfileInteractor
import com.kovapss.gitmobile.entities.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class ProfilePresenter(val username : String) : MvpPresenter<UserProfileView>() {

    @Inject lateinit var interactor : UserProfileInteractor

    private val cd = CompositeDisposable()

    private lateinit var user : User

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
        cd.add(interactor.getUser(username)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.setUserLogin(it.login)
                    user = it
                    viewState.hideProgress()
                    viewState.showOverviewScreen(it)
                }
                .subscribe())
    }

    fun overviewMenuOnClick() { viewState.showOverviewScreen(user) }

    fun gistsMenuOnClick(dataType : Int) {
        viewState.showProfileContentScreen(dataType, user.login)
    }

    fun starredMenuOnClick(dataType : Int) {
        viewState.showProfileContentScreen(dataType, user.login)
    }

    fun reposMenuOnClick(dataType : Int) {
        viewState.showProfileContentScreen(dataType, user.login)
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

}