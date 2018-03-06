package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.exceptions.NoNetworkException
import com.kovapss.gitmobile.model.repositories.UserRepository
import com.kovapss.gitmobile.model.system.NetworkHelper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject


class UserProfileInteractor {

    @Inject
    lateinit var repository: UserRepository

    @Inject
    lateinit var networkHelper: NetworkHelper

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun getUser(username: String): Single<User> = repository.getUser(username)
            .subscribeOn(Schedulers.io())

    fun getUserRepositories(userName: String): Single<List<Repository>> {
        checkConnection()
        return repository.getUserRepositories(userName)
                .subscribeOn(Schedulers.io())
    }

    fun getUserRepositories() : Single<List<Repository>>  {
        checkConnection()
        return repository.getAuthenticatedUserRepositories()
                .subscribeOn(Schedulers.io())
    }

    fun getUserStarred(username: String): Single<List<Repository>> {
        checkConnection()
        return repository.getUserStarred(username)
                .subscribeOn(Schedulers.io())
    }

    fun getUserStarred() = repository.getAuthenticatedUserStarred()
            .subscribeOn(Schedulers.io())

    fun getUserGists(username: String): Single<List<Gist>> {
        checkConnection()
        return repository.getUserGists(username)
                .subscribeOn(Schedulers.io())
    }

    fun getUserGists() = repository.getAuthenticatedUserGists()
            .subscribeOn(Schedulers.io())

    private fun checkConnection() {
        if (!networkHelper.isConnected()) throw NoNetworkException()
    }


}