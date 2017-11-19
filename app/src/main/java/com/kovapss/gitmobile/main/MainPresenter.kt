package com.kovapss.gitmobile.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.model.UserRepository
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject lateinit var repository : UserRepository

    private val cd = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Toothpick.inject(this, Toothpick.openScopes(APP_SCOPE, NETWORK_SCOPE))
//        cd.add(repository.getCurrentUserFromDb()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSuccess {
//                    with(it){
//                        viewState.setUserData(avatarUrl, name)
//                    }
//                }
//                .doOnError(Logger::d)
//                .subscribe()
//        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }
}