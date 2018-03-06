package com.kovapss.gitmobile.view.repositories.detail.content

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.HttpCodes
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.RepositoryDetailInteractor
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.commit.Commit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class RepositoryDetailContentPresenter(private val ownerLogin: String,
                                       private val repoName: String,
                                       private val dataType: Int)
    : MvpPresenter<RepositoryDetailContentView>() {

    @Inject
    lateinit var interactor: RepositoryDetailInteractor

    private val cd = CompositeDisposable()

    companion object {
        const val COMMIT_DATA_TYPE = 0
        const val CONTRIBUTORS_DATA_TYPE = 1
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
        viewState.showProgress()
        val data = when (dataType) {
            COMMIT_DATA_TYPE -> interactor.getRepositoryCommits(ownerLogin, repoName)
            CONTRIBUTORS_DATA_TYPE -> interactor.getRepositoryContributors(ownerLogin, repoName)
            else -> throw IllegalArgumentException("Unknown data type argument $dataType")
        }
        cd.add(data.observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.hideProgress()
                    viewState.showData(it)
                }
                .doOnError {
                    when (it) {
                        is HttpException -> {
                            if (it.code().toString() == HttpCodes.CODE_204) {
                                viewState.showEmptyResultError()
                            }
                        }
                    }
                }.subscribe())
    }

    fun commitClicked(commit: Commit) {

    }

    fun contributorClicked(contributor: User) {
        viewState.openUserDetailScreen(contributor.login)
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }
}