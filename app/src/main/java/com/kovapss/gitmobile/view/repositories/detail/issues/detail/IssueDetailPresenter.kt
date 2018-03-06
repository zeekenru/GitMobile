package com.kovapss.gitmobile.view.repositories.detail.issues.detail

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
class IssueDetailPresenter(private val issue : Issue, private val ownerLogin : String,
                           private val repositoryName : String) : MvpPresenter<IssueDetailView>() {

    @Inject lateinit var interactor: RepositoryDetailInteractor

    private val cd = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
        viewState.showIssue(issue)
        cd.add(interactor.getIssueComments(ownerLogin, repositoryName, issue.number)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { viewState.showComments(it) }
                .subscribe()
        )
    }

    fun openInBrowserMenuClicked() {
        viewState.openIssueInBrowser(issue.htmlUrl)
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

}