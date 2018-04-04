package com.kovapss.gitmobile.view.notifications

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.NotificationsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class NotificationsPresenter : MvpPresenter<NotificationsView>() {

    @Inject lateinit var interactor : NotificationsInteractor


    private val cd = CompositeDisposable()

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        cd.add(interactor.getNotifications(true)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.hideProgress()
                    viewState.showNotifications(it)
                }
                .subscribe())
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }
}