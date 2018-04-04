package com.kovapss.gitmobile.view.gists

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.GistsInteractor
import com.kovapss.gitmobile.entities.CreateGistData
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.exceptions.NotAuthenticatedUserException
import com.kovapss.gitmobile.model.system.NetworkHelper
import com.kovapss.gitmobile.model.system.PreferenceHelper
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class GistsPresenter : MvpPresenter<GistsView>() {

    @Inject lateinit var networkHelper: NetworkHelper

    @Inject lateinit var preferenceHelper: PreferenceHelper

    @Inject lateinit var interactor: GistsInteractor

    private var tabId = R.id.public_gists_item

    private val cd = CompositeDisposable()

    private var pageNumber = 1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        viewState.setFabVisible(preferenceHelper.getAuthStatus())
        loadGist(tabId)
    }

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun tabSelected(id: Int) {
        tabId = id
        cd.clear()
        loadGist(tabId)
    }


    fun itemOnClick(item: Gist) {
        cd.add(interactor.getGist(item.githubId)
                .map { it }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { viewState.openDetailGist(it) }
                .subscribe())

    }

    private fun loadGist(id: Int) {
        if (networkHelper.isConnected()) {
            try {
                cd.add(interactor.getGists(id, pageNumber)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError {
                            viewState.showEmptyResultError()
                        }
                        .doOnSuccess {
                            viewState.hideProgress()
                            if (it.isEmpty()) {
                                viewState.showEmptyResultError()
                            } else {
                                viewState.showGists(it)
                            }

                        }
                        .subscribe())
            } catch (exception: NotAuthenticatedUserException) {
                viewState.showNotAuthError()
            }

        } else viewState.showInternetError()
    }

    fun fabOnClick() {
        if (preferenceHelper.getAuthStatus()){
            viewState.openGistCreateView()
        } else {
            viewState.showNotAuthError()
        }

    }

    fun clickOnUpdate() {
        loadGist(tabId)
    }

    fun clickOnLogin() {
        viewState.openLoginDialogScreen()
    }

    fun loginSuccessful() {
        loadGist(tabId)
    }

    fun createGistDataReceived(gistData: CreateGistData) {
        cd.add(interactor
                .createGist(gistData)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { viewState.openDetailGist(it) }
                .subscribe())
    }

    fun onLoadMore() {
        pageNumber += 1
        Logger.d("Page need: $pageNumber")
        cd.add(interactor.getGists(tabId, pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { viewState.addGists(it) }
                .subscribe())
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

}