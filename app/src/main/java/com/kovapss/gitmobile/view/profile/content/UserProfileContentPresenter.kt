package com.kovapss.gitmobile.view.profile.content

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.UserProfileInteractor
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.repository.Repository
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject


@InjectViewState
class UserProfileContentPresenter(private val username: String, private val dataType: Int)
    : MvpPresenter<UserProfileContentView>() {

    @Inject
    lateinit var interactor: UserProfileInteractor

    private val cd = CompositeDisposable()

    companion object {
        const val REPOSITORY_DATA_TYPE = 0
        const val STARRED_DATA_TYPE = 1
        const val GISTS_DATA_TYPE = 2
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
        loadData()
    }


    private fun loadData() {
        viewState.showProgress()
        Logger.d("LoadDataType: $dataType ")
        val data = when (dataType) {
            REPOSITORY_DATA_TYPE -> interactor.getUserRepositories(username)
            STARRED_DATA_TYPE -> interactor.getUserStarred(username)
            GISTS_DATA_TYPE -> interactor.getUserGists(username)
            else -> throw IllegalArgumentException("Invalid data type: $dataType")
        }
        cd.add(data.observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.hideProgress()
                    if (it.isNotEmpty()) {
                        viewState.showData(it)
                    } else {
                        viewState.showEmptyResultError()
                    }
                }
//                .doOnError {
//                    when(it){
//                        is NoNetworkException -> viewState.showNetworkError()
//                    }
//                }
                .subscribe())

    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

    fun clickOnGist(gist: Gist) {
        viewState
    }

    fun clickOnRepository(repo: Repository) {
        viewState.openRepositoryDetailScreen(repo)
    }

    fun networkErrorBtnClicked() {
        viewState.hideNetworkError()
        loadData()
    }

    fun emptyResultErrorBtnCLicked() {
        viewState.hideEmptyResultError()
        loadData()
    }


}