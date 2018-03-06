package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.exceptions.EmptyResultException
import com.kovapss.gitmobile.exceptions.NotAuthenticatedUserException
import com.kovapss.gitmobile.model.repositories.GistsRepository
import com.kovapss.gitmobile.model.system.NetworkHelper
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.Observable
import io.reactivex.Single
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


    fun loadGistsCase(id: Int): Single<List<Gist>> {
        val data = when (id) {
            R.id.my_gists_item -> {
                if (!preferenceHelper.getAuthStatus()) {
                    throw NotAuthenticatedUserException()
                }
                repository.getUserGists(preferenceHelper.getCurrentUserLogin())

            }
            R.id.starred_gists_item -> {
                if (!preferenceHelper.getAuthStatus()) {
                    throw NotAuthenticatedUserException()
                }
                repository.getStarredGists(preferenceHelper.getAccessToken())
            }

            R.id.public_gists_item -> repository.getPublicGists().subscribeOn(Schedulers.io())

            else -> throw IllegalArgumentException("Unknown tab tag")
        }
        return data.subscribeOn(Schedulers.io())
    }

}