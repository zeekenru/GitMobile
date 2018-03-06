package com.kovapss.gitmobile.model.api

import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.entities.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface UserService {

    @GET("users/{name}")
    fun getUser(@Path("name") name: String): Single<User>

    @GET("user")
    fun getAuthenticatedUser(@Query("access_token") accessToken: String): Single<User>

    @GET("users/{name}/repos")
    fun getUserRepositories(@Path("name") username: String) : Single<List<Repository>>

    @GET("user/repos")
    fun getAuthenticatedUserRepositories() : Single<List<Repository>>

    @GET("users/{name}/starred")
    fun getUserStarred(@Path("name") username: String) : Single<List<Repository>>

    @GET("user/starred")
    fun getAuthenticatedUserStarred() : Single<List<Repository>>

    @GET("users/{name}/gists")
    fun getUserGists(@Path("name") username: String) : Single<List<Gist>>

    @GET("user/gists")
    fun getAuthenticatedUserGists() : Single<List<Gist>>

}