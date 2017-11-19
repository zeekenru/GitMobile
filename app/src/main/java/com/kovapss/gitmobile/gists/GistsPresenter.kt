package com.kovapss.gitmobile.gists

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.GistsInteractor
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.system.NetworkHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class GistsPresenter : MvpPresenter<GistsView>() {

    @Inject lateinit var networkHelper: NetworkHelper

    @Inject lateinit var interactor: GistsInteractor

    private val defaultTag = "Public"

    private val cd = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        loadGist(defaultTag)
    }

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun tabSelected(tag: String) {
        loadGist(tag)
    }


    fun itemOnClick(item: Gist) {

    }

    private fun loadGist(tag: String) {
        if (networkHelper.isConnected()) {
            cd.add(interactor.loadGistsCase(tag)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext {
                        viewState.hideProgress()
                        viewState.showGists(it)
                    }
                    .subscribe()
            )
        } else viewState.showInternetError()
    }

    fun fabOnClick() {
        viewState.openGistCreateView()
    }

}