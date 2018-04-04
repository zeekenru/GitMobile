package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.HttpCodes
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.CreateGistData
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.exceptions.EmptyResultException
import com.kovapss.gitmobile.exceptions.NotAuthenticatedUserException
import com.kovapss.gitmobile.model.repositories.GistsRepository
import com.kovapss.gitmobile.model.system.NetworkHelper
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject


class GistsInteractor {

    private companion object {
        const val MY_TAG = "My"
        const val STARRED_TAG = "Starred"
        const val PUBLIC_TAG = "Public"
    }

    @Inject
    lateinit var repository: GistsRepository

    @Inject
    lateinit var networkHelper: NetworkHelper

    @Inject
    lateinit var preferenceHelper: PreferenceHelper


    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun createGist(createGistData: CreateGistData) : Single<Gist> {
        return repository.createGist(createGistData)
                .filter { it.code().toString() == HttpCodes.CODE_201 }
                .toSingle()
                .map { it.body() }
                .subscribeOn(Schedulers.io())
    }

    fun getGist(githubId : String) : Single<Gist> = repository.getGist(githubId)
            .map { it.body() }
            .subscribeOn(Schedulers.io())

    fun getGists(id: Int, page : Int): Single<List<Gist>> {
        val data = when (id) {
            R.id.my_gists_item -> {
                if (!preferenceHelper.getAuthStatus()) {
                    throw NotAuthenticatedUserException()
                }
                repository.getUserGists(preferenceHelper.getCurrentUserLogin(), page)

            }
            R.id.starred_gists_item -> {
                if (!preferenceHelper.getAuthStatus()) {
                    throw NotAuthenticatedUserException()
                }
                repository.getStarredGists(page)
            }

            R.id.public_gists_item -> repository.getPublicGists(page).subscribeOn(Schedulers.io())

            else -> throw IllegalArgumentException("Unknown tab tag")
        }
        return data.map { it.body() }.subscribeOn(Schedulers.io())
    }

}