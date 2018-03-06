package com.kovapss.gitmobile.view.repositories.detail.files

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.domain.RepositoryDetailInteractor
import com.kovapss.gitmobile.entities.repository.RepositoryFile
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import toothpick.Toothpick
import javax.inject.Inject

@InjectViewState
class RepositoryFilesPresenter(private val ownerLogin: String,
                               private val repoName: String) : MvpPresenter<RepositoryFilesView>() {

    @Inject
    lateinit var interactor: RepositoryDetailInteractor

    private var lastPath = ""

    private var branch = ""

    private val cd = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
        cd.add(interactor.getRepositoryBranches(ownerLogin, repoName)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    val namesList = List(it.size, { index -> it[index].name })
                    Logger.d("Branches: $namesList")
                    branch = namesList.first()
                    getFiles(branch)
                    viewState.setBranches(namesList)
                }.subscribe())
    }


    fun onBranchItemSelected(branch: String) {
        viewState.showProgress()
        getFiles(branch)
    }

    private fun getFiles(branch: String, path : String = "") {
        cd.add(interactor.getRepositoryFiles(ownerLogin, repoName, path, branch)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    viewState.showRepoContent(it)
                    viewState.hideProgress()
                }
                .subscribe()
        )
    }


    fun filesItemClicked(file: RepositoryFile) {
        if (file.type == "dir") {
            viewState.showProgress()
            lastPath = file.path
            getFiles(branch, file.name)
        } else {
            viewState.openFileViewer(file)
        }
    }

    fun obBackPressed(){

    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }

}