package com.kovapss.gitmobile.view.repositories.detail.collaborators

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.CollaboratorsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class AddCollaboratorPresenter : MvpPresenter<AddCollaboratorView>() {

    @Inject lateinit var interactor: CollaboratorsInteractor

    private val cd = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun textChanged(query: String) {
        cd.add(interactor.searchUserAsCollaborator(query)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { viewState.showUsers(it) }
                .subscribe())
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }
}