package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.HttpCodes
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.exceptions.NoNetworkException
import com.kovapss.gitmobile.model.repositories.UserRepository
import com.kovapss.gitmobile.model.system.NetworkHelper
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
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


    fun getUserStarred(username: String): Single<List<Repository>> {
        checkConnection()
        return repository.getUserStarred(username)
                .subscribeOn(Schedulers.io())
    }

    fun getUserGists(username: String): Single<List<Gist>> {
        checkConnection()
        return repository.getUserGists(username)
                .subscribeOn(Schedulers.io())
    }

    fun checkUserIsBlocked(username: String): Single<Boolean> =
            repository.checkUserIsBlocked(username)
                    .map { it.code().toString() == HttpCodes.CODE_204 }
                    .onErrorReturn {
                        with(it as HttpException) { it.code().toString() == HttpCodes.CODE_204 }
                    }
                    .subscribeOn(Schedulers.io())

    fun blockUser(username: String): Completable = Completable.fromSingle(
            repository.blockUser(username)
            .subscribeOn(Schedulers.io()))

    fun unblockUser(username: String) : Completable = Completable.fromSingle(
            repository.unblockUser(username).subscribeOn(Schedulers.io()))

    fun checkIsUserFollowed(username: String) :  Single<Boolean> =
            repository.checkUserIsFollowed(username)
                    .map { it.code().toString() == HttpCodes.CODE_204 }
                    .onErrorReturn {
                        with(it as HttpException) { it.code().toString() == HttpCodes.CODE_204 }
                    }
                    .subscribeOn(Schedulers.io())

    fun followUser(username: String) : Completable = Completable.fromSingle(
            repository.followUser(username).subscribeOn(Schedulers.io())
    )

    fun unfollowUser(username: String) : Completable = Completable.fromSingle(
            repository.unfollowUser(username).subscribeOn(Schedulers.io())
    )

    private fun checkConnection() {
        if (!networkHelper.isConnected()) throw NoNetworkException()
    }


}