package com.kovapss.gitmobile.model

import com.kovapss.gitmobile.entities.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface UserService {
    @GET("users/{name}")
    fun getUser(@Path("name") name : String) : Observable<User>

    @GET("user")
    fun getCurrentUser(@Query("access_token") accessToken : String) : Observable<User>
}