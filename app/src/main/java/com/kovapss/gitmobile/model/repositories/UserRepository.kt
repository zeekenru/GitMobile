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

    fun getCurrentUserFromNetwork(accessToken: String): Single<User> {
        return apiService.getAuthenticatedUser(accessToken)
    }

    fun getUserRepositories(userName : String) : Single<List<Repository>>
            = apiService.getUserRepositories(userName)

    fun getAuthenticatedUserRepositories() : Single<List<Repository>>
            = apiService.getAuthenticatedUserRepositories()


    fun getUserStarred(username: String) : Single<List<Repository>>
            = apiService.getUserStarred(username)

    fun getAuthenticatedUserStarred() : Single<List<Repository>>
            = apiService.getAuthenticatedUserStarred()

    fun getUserGists( username: String) :
            Single<List<Gist>>  = apiService.getUserGists(username)

    fun getAuthenticatedUserGists() : Single<List<Gist>>  = apiService.getAuthenticatedUserGists()


    fun getCurrentUserFromDb(): Single<UserDb> = db.userDao().getUser().subscribeOn(Schedulers.io())

    private fun saveUserToDb(user: User) {
        with(user){
            val userDb = UserDb(0, id,  avatarUrl, name, login, location,
                    email, bio, followers, following)
            db.userDao().insertUser(userDb)
        }
    }

}