package com.kovapss.gitmobile.view.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.domain.MainInteractor
import com.kovapss.gitmobile.entities.CreateGistData
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.repository.CreateRepositoryModel
import com.kovapss.gitmobile.model.repositories.UserRepository
import com.kovapss.gitmobile.model.system.PreferenceHelper
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
    lateinit var interactor : MainInteractor

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val cd = CompositeDisposable()

    companion object {
        private const val CONTINUE_WITH_GIST = 0
        private const val CONTINUE_WITH_REPO = 1
        private const val CONTINUE_WITH_PROFILE = 2
    }

    private var continueWithAuth = -1

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
    }


    fun clickOnHeader() {
        if (preferenceHelper.getAuthStatus()){
            viewState.openUserProfileScreen(preferenceHelper.getCurrentUserLogin())
        } else {
            continueWithAuth = CONTINUE_WITH_PROFILE
            viewState.showLoginDialog()
        }

    }

    fun clickOnLogin() {
        viewState.openLoginScreen()
    }

    fun loginSuccessful() {
        if (preferenceHelper.getAuthStatus()){
            when (continueWithAuth){
                CONTINUE_WITH_REPO -> viewState.openCreateRepositoryScreen()
                CONTINUE_WITH_GIST -> viewState.openCreateGistScreen()
                CONTINUE_WITH_PROFILE -> viewState.openUserProfileScreen(preferenceHelper.getCurrentUserLogin())
            }
        }
    }

    fun createRepoFabClicked() {
        if (preferenceHelper.getAuthStatus()) {
            viewState.openCreateRepositoryScreen()
        } else{
            viewState.showLoginDialog()
            continueWithAuth = CONTINUE_WITH_REPO
        }
    }

    fun createGistFabClicked() {
        if (preferenceHelper.getAuthStatus()){
            viewState.openCreateGistScreen()
        } else{
            viewState.showLoginDialog()
            continueWithAuth = CONTINUE_WITH_GIST
        }

    }

    fun repositoryDataReceived(data : CreateRepositoryModel) {
        cd.add(interactor.createRepo(data)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { viewState.openRepositoryDetailScreen(it) }
                .subscribe())
    }
    fun gistCreateDataReceived(createGistData: CreateGistData) {
        cd.add(interactor.createGist(createGistData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({gist: Gist -> viewState.openGistDetailScreen(gist) }))
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }


}