package com.kovapss.gitmobile.launch

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.domain.LaunchInteractor
import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.system.NetworkHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class LaunchPresenter : MvpPresenter<LaunchView>() {

    @Inject lateinit var networkHelper: NetworkHelper

    @Inject lateinit var interactor : LaunchInteractor

    private val cd = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Toothpick.inject(this, Toothpick.openScopes(APP_SCOPE, NETWORK_SCOPE))
    }

    fun loginClick() {
        if (networkHelper.isConnected()) {
            viewState.showProgress()
            viewState.startLoginActivity()

        } else viewState.showInternetError()

    }

    fun skipClick() {
        viewState.startMainActivity()
    }

    fun clickTryConnect() {
        viewState.showProgress()
        if (networkHelper.isConnected()) viewState.startLoginActivity()
        else {
            viewState.hideProgress()
            viewState.showInternetError()
        }
    }

    fun oauthResult() {
        viewState.showProgress()
        cd.add(interactor.getUserCase()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { viewState.showWelcome(it) }
                .delay(2, TimeUnit.SECONDS)
                .doOnComplete {
                    viewState.startMainActivity()
                }
                .subscribe()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }
}