package com.kovapss.gitmobile.view.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.UserProfileInteractor
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class ProfilePresenter(val username: String) : MvpPresenter<UserProfileView>() {

    @Inject
    lateinit var interactor: UserProfileInteractor

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val cd = CompositeDisposable()

    private lateinit var user: User

    private var isFollowed = false

    private var isBlocked = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
        cd.add(interactor.getUser(username)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.setUserLogin(it.login)
                    user = it
                    viewState.hideProgress()
                    viewState.showOverviewScreen(it)
                }
                .subscribe())
    }

    fun overviewMenuOnClick() {
        viewState.showOverviewScreen(user)
    }

    fun gistsMenuOnClick(dataType: Int) {
        viewState.showProfileContentScreen(dataType, user.login)
    }

    fun starredMenuOnClick(dataType: Int) {
        viewState.showProfileContentScreen(dataType, user.login)
    }

    fun reposMenuOnClick(dataType: Int) {
        viewState.showProfileContentScreen(dataType, user.login)
    }

    fun blockClicked() {
        if (isBlocked) {
            cd.add(interactor.unblockUser(username)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { viewState.setUserBlockedStatus(false) }
                    .subscribe())
        } else {
            cd.add(interactor.blockUser(username).observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { viewState.setUserBlockedStatus(true) }
                    .subscribe())
        }
    }

    fun followClicked() {
        if (isFollowed) {
            cd.add(interactor.unfollowUser(username)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { viewState.setUserFollowedStatus(false) }
                    .subscribe())
        } else {
            cd.add(interactor.followUser(username)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { viewState.setUserFollowedStatus(true) }
                    .subscribe())
        }
    }

    fun optionsMenuPrepared() {
        if (preferenceHelper.getAuthStatus() && username != preferenceHelper.getCurrentUserLogin()) {
            cd.addAll(interactor.checkUserIsBlocked(username)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess {
                        isBlocked = it
                        viewState.setUserBlockedStatus(it)
                    }
                    .subscribe(),
                    interactor.checkIsUserFollowed(username)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSuccess {
                                isFollowed = it
                                viewState.setUserFollowedStatus(it)
                            }
                            .subscribe())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

}