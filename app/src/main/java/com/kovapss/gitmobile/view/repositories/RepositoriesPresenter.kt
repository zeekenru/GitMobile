package com.kovapss.gitmobile.view.repositories

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.RepositoriesInteractor
import com.kovapss.gitmobile.entities.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class RepositoriesPresenter : MvpPresenter<RepositoriesView>() {

    @Inject lateinit var interactor: RepositoriesInteractor

    private val cd = CompositeDisposable()

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        cd.add(interactor.getPublicRepositories()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.hideProgress()
                    viewState.showData(it)
                }.subscribe())
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

    fun repositoryClicked(repository: Repository) {
        viewState.openRepositoryDetailScreen(repository)
    }

}