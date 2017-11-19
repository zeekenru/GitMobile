package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.model.GistsRepository
import com.kovapss.gitmobile.system.NetworkHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject


class GistsInteractor {

    private companion object {
        const val MY_TAG = "My"
        const val STARRED_TAG = "Starred"
        const val PUBLIC_TAG = "Public"
    }

    @Inject lateinit var repository: GistsRepository

    @Inject lateinit var networkHelper: NetworkHelper

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }


    fun loadGistsCase(tag: String): Observable<List<Gist>> {
        return when (tag) {
//            MY_TAG -> repository.getUserGists("zeekenru").subscribeOn(Schedulers.io())
//            STARRED_TAG -> repository.getStarredGists().subscribeOn(Schedulers.io())
            PUBLIC_TAG -> repository.getPublicGists().subscribeOn(Schedulers.io())
            else -> throw IllegalArgumentException("Unknown tab tag")
        }
    }

}