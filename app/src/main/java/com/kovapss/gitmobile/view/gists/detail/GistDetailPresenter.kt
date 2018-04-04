package com.kovapss.gitmobile.view.gists.detail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.GistDetailInteractor
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.exceptions.NotAuthenticatedUserException
import com.kovapss.gitmobile.model.system.PreferenceHelper
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject


@InjectViewState
class GistDetailPresenter(private val gist: Gist) : MvpPresenter<GistDetailView>() {

    @Inject
    lateinit var interactor: GistDetailInteractor

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val cd: CompositeDisposable = CompositeDisposable()

    private var isStarred = false

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }


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
        } catch (exception: NotAuthenticatedUserException) {

        }

        viewState.hideProgress()
        Logger.d(gist)
    }


    fun clickOnStar() {
        if (!isStarred) {
            try {
                cd.add(interactor.starGist(gist.githubId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            isStarred = true
                            viewState.setStarredStatus(true)
                        }
                        .subscribe())
            } catch (exception: NotAuthenticatedUserException) {
                viewState.showLoginDialog()
            }
        } else {
            cd.add(interactor.unstarGist(gist.githubId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete {
                        isStarred = false
                        viewState.setStarredStatus(false)
                    }
                    .subscribe())
        }
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

    fun clickOnDelete() {
        viewState.showDeleteDialog()
    }

    fun optionsMenuPrepared() {
        if (preferenceHelper.getAuthStatus()) {
            viewState.showEditMenu()

        }
    }

    fun deleteConfirmed() {
        cd.add(interactor.deleteGist(gist.githubId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { viewState.returnBack() }
                .subscribe())
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }


}