package com.kovapss.gitmobile.view.repositories.detail.issues

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.RepositoryDetailInteractor
import com.kovapss.gitmobile.entities.issue.Issue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class RepositoryIssuesPresenter(private val ownerLogin : String, private val repoName : String)
    : MvpPresenter<RepositoryIssuesView>() {

    @Inject lateinit var interactor: RepositoryDetailInteractor

    private val cd = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
        cd.add(interactor.getRepositoryIssues(ownerLogin, repoName)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.hideProgress()
                    if (it.isNotEmpty()) {
                        viewState.showData(it)
                    } else {
                        viewState.showEmptyResultError()
                    }
                }
                .subscribe()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

    fun clickOnIssue(issue : Issue) {
        viewState.openIssueDetailScreen(issue, ownerLogin, repoName)
    }
}