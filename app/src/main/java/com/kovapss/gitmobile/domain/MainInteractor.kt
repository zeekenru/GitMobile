package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.CreateGistData
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.repository.CreateRepositoryModel
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.model.repositories.GistsRepository
import com.kovapss.gitmobile.model.repositories.ReposRepository
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject


class MainInteractor {

    @Inject
    lateinit var reposRepository: ReposRepository

    @Inject
    lateinit var gistsRepository: GistsRepository

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    fun createRepo(data: CreateRepositoryModel): Single<Repository> = reposRepository.createRepository(data)
            .map { it.body() }
            .subscribeOn(Schedulers.io())


    fun createGist(data: CreateGistData): Single<Gist> = gistsRepository.createGist(data)
            .map { it.body() }
            .subscribeOn(Schedulers.io())

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }
}