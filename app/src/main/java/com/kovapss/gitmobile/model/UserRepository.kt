package com.kovapss.gitmobile.model

import com.kovapss.gitmobile.db.AppDatabase
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.UserDb
import com.kovapss.gitmobile.system.NetworkHelper
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UserRepository {

    @Inject lateinit var apiService: UserService

    @Inject lateinit var db: AppDatabase

    fun getUser(name: String): Observable<User> {
        return apiService.getUser(name)
    }

    fun getCurrentUserFromNetwork(accessToken: String): Observable<User> {
        return apiService.getCurrentUser(accessToken)
                .doOnNext({saveUserToDb(it)})
    }

    fun getCurrentUserFromDb(): Single<UserDb> = db.userDao().getUser().subscribeOn(Schedulers.io())

    private fun saveUserToDb(user: User) {
        with(user){
            val userDb = UserDb(0, id,  avatarUrl, name, location,
                    email, bio, followers, following)
            db.userDao().insertUser(userDb)
        }
    }

}