package com.kovapss.gitmobile.view.login

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Constants.CLIENT_ID
import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.domain.LoginInteractor
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {


    @Inject
    lateinit var interactor: LoginInteractor

    private val cd: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Toothpick.inject(this, Toothpick.openScopes(NETWORK_SCOPE, APP_SCOPE))
        viewState.loadUrl(interactor.getAuthUrlCase())
    }

    fun onPageStarted() {
        viewState.showProgress()
    }

    fun onPageFinished() {
        viewState.hideProgress()
    }


    fun shouldOverrideUrlLoading(url: String): Boolean {
        when (true) {

            url.contains(CLIENT_ID) -> {
                viewState.loadUrl(url)
                return true
            }

            url.contains("code", true) -> {
                viewState.showProgress()
                viewState.hideKeyboard()
                val uri = Uri.parse(url)
                val code = uri.getQueryParameter("code")
                Logger.d("Code : $code")
                cd.add(interactor.authUserCase(code)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(Logger::d)
                        .doOnSubscribe { viewState.showProgress() }
                        .doOnComplete { viewState.setOkResult() }
                        .subscribe()
                )
                return true
            }
        }
        return true

    }


    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }


}