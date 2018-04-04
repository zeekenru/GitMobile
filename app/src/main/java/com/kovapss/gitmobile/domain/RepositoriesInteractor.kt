package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.model.repositories.ReposRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject


class RepositoriesInteractor {

    @Inject lateinit var repository : ReposRepository

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun getPublicRepositories() : Single<List<Repository>> = repository.getPublicRepositories()
            .map { it.body() }
            .subscribeOn(Schedulers.io())


}