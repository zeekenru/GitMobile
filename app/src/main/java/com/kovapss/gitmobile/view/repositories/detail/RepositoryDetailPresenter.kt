package com.kovapss.gitmobile.view.repositories.detail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.RepositoryDetailInteractor
import com.kovapss.gitmobile.entities.repository.Readme
import com.kovapss.gitmobile.entities.repository.Repository
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class RepositoryDetailPresenter(private val repo: Repository) : MvpPresenter<RepositoryDetailView>() {

    @Inject
    lateinit var interactor: RepositoryDetailInteractor

    private val cd = CompositeDisposable()

    private lateinit var readme: Readme

    companion object {
        private const val COMMIT_DATA_TYPE = 0
        private const val CONTRIBUTORS_DATA_TYPE = 1
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Logger.d("was getting repository: $repo")
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
        viewState.showProgress()
        cd.addAll(interactor.getRepositoryReadme(repo.owner.login, repo.name)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    readme = it
                    viewState.setRepositoryData(repo)
                    viewState.showReadmeScreen(it)
                    viewState.hideProgress()
                }
                .subscribe(),
                interactor.getRepositoryTopics(repo.owner.login, repo.name)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { it.topics }
                        .doOnSuccess {
                            Logger.d("Topics: $it")
                            if (it.isNotEmpty()) {
                                viewState.showRepositoryTopics(it)
                            }
                        }
                        .subscribe())

    }

    fun clickStar() {

    }

    fun clickWatch() {

    }

    override fun onDestroy() {
        cd.clear()
        super.onDestroy()
    }

    fun menuIssuesClicked() {
        viewState.showIssuesScreen(repo.owner.login, repo.name)
    }

    fun menuReadmeClicked() {
        viewState.showReadmeScreen(readme)
    }

    fun menuCommitsClicked() {
        viewState.showContentScreen(repo.owner.login, repo.name, COMMIT_DATA_TYPE)
    }

    fun menuContributorsClicked() {
        viewState.showContentScreen(repo.owner.login, repo.name, CONTRIBUTORS_DATA_TYPE)
    }

    fun menuFilesClicked() {
        viewState.showFilesScreen(repo.owner.login, repo.name)
    }

    fun clickOwnerAvatar() { viewState.openUserScreen(repo.owner.login) }
}