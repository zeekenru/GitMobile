package com.kovapss.gitmobile.view.search

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.domain.SearchInteractor
import com.kovapss.gitmobile.entities.FiltersData
import com.kovapss.gitmobile.entities.issue.Issue
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.commit.Commit
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import java.net.SocketTimeoutException
import javax.inject.Inject

@InjectViewState
class SearchPresenter : MvpPresenter<SearchView>() {

    @Inject lateinit var interactor: SearchInteractor

    private lateinit var query: String

    private var cd = CompositeDisposable()

    init {
        Toothpick.inject(this, Toothpick.openScopes(APP_SCOPE, NETWORK_SCOPE))
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        reposItemSelected()
    }

    fun filtersChanged(data: FiltersData) {

    }

    fun receivedQuery(query: String) {
        this.query = query
        reposItemSelected()
    }

    fun reposItemSelected() {
        cd.add(interactor.getRepositories(query)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.hideProgress()
                    viewState.showSearchResults(it)
                }
                .doOnError {
                    if (it is SocketTimeoutException){
                        Logger.d("timeout exception")
                    }
                }
                .subscribe()
        )
    }

    fun commitsItemSelected() {
        cd.add(interactor.getCommits(query)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.hideProgress()
                    viewState.showSearchResults(it)
                }
                .subscribe()
        )
    }

    fun issuesItemSelected() {
        cd.add(interactor.getIssues(query)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.hideProgress()
                    viewState.showSearchResults(it)
                }
                .subscribe()
        )
    }

    fun usersItemSelected() {
        cd.add(interactor.getUsers(query)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.hideProgress()
                    viewState.showSearchResults(it)
                }
                .subscribe()
        )
    }

    fun repositoryClicked(repository: Repository) {
        viewState.openRepositoryDetailScreen(repository)
    }

    fun commitClicked(commit: Commit) {

    }

    fun issueClicked(issue: Issue) {

    }
    fun userClicked(user: User) {
        viewState.openUserProfileScreen(user.login)
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }




}