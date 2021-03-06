package com.kovapss.gitmobile.model.api

import com.kovapss.gitmobile.entities.AccessData
import io.reactivex.Observable
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthService {

    @POST("access_token")
    @Headers("Accept: application/json")
    fun authorize(@Query("client_id") clientId: String,
                  @Query("client_secret") clientSecret: String,
                  @Query("code") code: String) : Observable<AccessData>
}