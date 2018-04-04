package com.kovapss.gitmobile.model.repositories

import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.model.db.AppDatabase
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.UserDb
import com.kovapss.gitmobile.model.api.UserService
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UserRepository {

    @Inject lateinit var apiService: UserService

    @Inject lateinit var db: AppDatabase

    @Inject lateinit var preferenceHelper: PreferenceHelper

    fun getUser(name: String): Single<User> {
        return apiService.getUser(name)
    }

    fun getCurrentUserFromNetwork(): Single<User> {
        return apiService.getAuthenticatedUser()
    }

    fun getUserRepositories(userName: String): Single<List<Repository>> = apiService.getUserRepositories(userName)


    fun getUserStarred(username: String): Single<List<Repository>> = apiService.getUserStarred(username)


    fun getUserGists(username: String):
            Single<List<Gist>> = apiService.getUserGists(username)


    fun checkUserIsBlocked(username: String) = apiService.checkUserIsBlocked(username)

    fun blockUser(username: String) = apiService.blockUser(username)

    fun unblockUser(username: String) = apiService.unblockUser(username)

    fun checkUserIsFollowed(username: String) = apiService.checkUserIsFollowed(username)

    fun followUser(username: String) = apiService.followUser(username)

    fun unfollowUser(username: String) = apiService.unfollowUser(username)

}