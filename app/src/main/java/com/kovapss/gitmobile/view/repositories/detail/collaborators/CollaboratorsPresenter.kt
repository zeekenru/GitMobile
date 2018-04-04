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
class CollaboratorsPresenter(val ownerLogin: String, val repositoryName: String)
    : MvpPresenter<CollaboratorsView>() {

    @Inject
    lateinit var interactor: CollaboratorsInteractor

    private val cd = CompositeDisposable()

    private var page = 1

    private var deletedUsername = ""

    private var deletedPosition = 0

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        cd.add(getCollaborators(page)
                .doOnSuccess {
                    if (it.isNotEmpty()) {
                        viewState.showCollaborators(it)
                        if (it.size < 30) viewState.setNoMoreItems()
                    } else {
                        viewState.showEmptyError()
                    }
                }
                .doOnError { viewState.showEmptyError() }
                .subscribe())

    }


    fun itemClicked(username: String) {
        viewState.openUserProfileScreen(username)
    }

    fun deleteClicked(username: String, position: Int) {
        viewState.showDeleteDialog()
        deletedUsername = username
        deletedPosition = position
    }

    fun fabClicked() {
        viewState.showAddCollaboratorScreen()
    }

    fun loadMore() {
        page += 1
        cd.add(getCollaborators(page)
                .doOnSuccess { viewState.addCollaborators(it) }
                .subscribe())
    }


    fun deleteConfirmed() {
        cd.add(interactor.deleteRepositoryCollaborator(ownerLogin, repositoryName, deletedUsername)
                .doOnSuccess {
                    if (it) {
                        viewState.removeItem(deletedPosition)
                    }
                }
                .subscribe())
        viewState.showDeleteConfirm(deletedUsername)
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

    private fun getCollaborators(page: Int) =
            interactor.getRepositoryCollaborators(ownerLogin, repositoryName, page)
                    .observeOn(AndroidSchedulers.mainThread())


}