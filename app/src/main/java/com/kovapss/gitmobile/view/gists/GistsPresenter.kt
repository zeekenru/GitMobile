package com.kovapss.gitmobile.view.gists

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.GistsInteractor
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.exceptions.EmptyResultException
import com.kovapss.gitmobile.exceptions.NotAuthenticatedUserException
import com.kovapss.gitmobile.model.system.NetworkHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class GistsPresenter : MvpPresenter<GistsView>() {

    @Inject lateinit var networkHelper: NetworkHelper

    @Inject lateinit var interactor: GistsInteractor

    private var tabId = R.id.public_gists_item

    private val cd = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        loadGist(tabId)
    }

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun tabSelected(id: Int) {
        tabId = id
        loadGist(tabId)
    }


    fun itemOnClick(item: Gist) {
        viewState.openDetailGist(item)
    }

    private fun loadGist(id: Int) {
        if (networkHelper.isConnected()) {
            try {
                cd.add(interactor.loadGistsCase(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError {
                            viewState.showEmptyResultError()
                        }
                        .doOnSuccess {
                            viewState.hideProgress()
                            if (it.isEmpty()){
                                viewState.showEmptyResultError()
                            } else{
                                viewState.showGists(it)
                            }

                        }
                        .subscribe())
            } catch (exception : NotAuthenticatedUserException){ viewState.showNotAuthError() }

        } else viewState.showInternetError()
    }

    fun fabOnClick() {
        viewState.openGistCreateView()
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

}