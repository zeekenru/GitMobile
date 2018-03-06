package com.kovapss.gitmobile.view.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.model.repositories.UserRepository
import com.kovapss.gitmobile.model.system.PreferenceHelper
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var repository: UserRepository

    @Inject
    lateinit var preferenceHelper: PreferenceHelper


    private val cd = CompositeDisposable()

    private var username = "GeorgiyDemo"

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Toothpick.inject(this, Toothpick.openScopes(APP_SCOPE, NETWORK_SCOPE))
        if (preferenceHelper.getAuthStatus()) {
            cd.add(repository.getUser(preferenceHelper.getCurrentUserLogin())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { viewState.setUserData(it.avatarUrl, it.name) }
                    .subscribe())
        } else {

        }


// repository.getCurrentUserFromDb()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnSuccess {
//                        Logger.d("User from db: $it")
//                        viewState.setUserData(it.avatarUrl, it.name)
//                    }
//                    .doOnError(Logger::d)
//                    .subscribe()


    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

    fun clickOnHeader() {
        if (preferenceHelper.getAuthStatus()){
            viewState.openUserProfileScreen(preferenceHelper.getCurrentUserLogin())
        } else {
            viewState.showLoginDialog()
        }

    }

    fun clickOnLogin() {
        viewState.openLoginScreen()
    }

    fun loginSuccessful() {
        if (preferenceHelper.getAuthStatus()){
            viewState.openUserProfileScreen(preferenceHelper.getCurrentUserLogin())
        }
    }
}