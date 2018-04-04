package com.kovapss.gitmobile.model.api

import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.repository.Repository
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface UserService {

    @GET("users/{name}")
    fun getUser(@Path("name") name: String): Single<User>

    @GET("user")
    fun getAuthenticatedUser(): Single<User>

    @GET("users/{name}/repos")
    fun getUserRepositories(@Path("name") username: String): Single<List<Repository>>


    @GET("users/{name}/starred")
    fun getUserStarred(@Path("name") username: String): Single<List<Repository>>


    @GET("users/{name}/gists")
    fun getUserGists(@Path("name") username: String): Single<List<Gist>>


    @GET("/user/blocks/{username}")
    @Headers("Accept: application/vnd.github.giant-sentry-fist-preview+json")
    fun checkUserIsBlocked(@Path("username") username: String): Single<Response<ResponseBody>>

    @PUT("/user/blocks/{username}")
    fun blockUser(@Path("username") username: String): Single<Response<ResponseBody>>

    @DELETE("/user/blocks/{username}")
    fun unblockUser(@Path("username") username: String): Single<Response<ResponseBody>>

    @GET("/user/following{username}")
    fun checkUserIsFollowed(@Path("username") username: String): Single<Response<ResponseBody>>

    @PUT("/user/following/{username}")
    @Headers("Content-Length: 0")
    fun followUser(@Path("username") username: String): Single<Response<ResponseBody>>

    @DELETE("/user/following/{username}")
    fun unfollowUser(@Path("username") username: String): Single<Response<ResponseBody>>

}