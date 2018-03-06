package com.kovapss.gitmobile.view.gists.detail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.GistDetailInteractor
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.exceptions.NotAuthenticatedUserException
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject


@InjectViewState
class GistDetailPresenter(private val gist: Gist) : MvpPresenter<GistDetailView>() {

    @Inject
    lateinit var interactor: GistDetailInteractor

    private val cd: CompositeDisposable = CompositeDisposable()

    private var isStarred = false


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        viewState.showGist(gist, interactor.getShowLineNumbers())
        if (gist.comments == 0) {
            viewState.hideComments()
        } else {
            cd.add(
                    interactor.getComments(gist.githubId)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSuccess {
                                viewState.showComments(it)
                                Logger.d("Comments was getting: ${it.size}")
                            }
                            .doOnError(Logger::d)
                            .subscribe()
            )
        }
        try {
            cd.add(interactor.checkGistIsStarred(gist.githubId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess {
                        isStarred = it
                        viewState.setGistStarredStatus(isStarred)
                    }
                    .subscribe())
        } catch (exception : NotAuthenticatedUserException){
            
        }

        viewState.hideProgress()
        Logger.d(gist)
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }


    fun clickOnStar() {
        if (!isStarred) {
            try {
                interactor.starGist(gist.githubId)
            } catch (exception: NotAuthenticatedUserException) {
                viewState.showLoginDialog()
            }
        }
    }

    fun clickOnFork() {

    }

    fun clickOnOpenInBrowser() {
        viewState.openGistInBrowser(gist.htmlUrl)
    }

    fun clickOnSendComment(commentText: String) {
        cd.add(interactor.sendComment(gist.githubId, commentText).doOnComplete {
            interactor.getComments(gist.githubId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { viewState.showComments(it) }
                    .subscribe()
        }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun clickOnUpdate() {

    }

    fun clickOnLogin() {

    }


}