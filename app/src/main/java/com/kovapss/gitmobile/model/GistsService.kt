package com.kovapss.gitmobile.model

import com.kovapss.gitmobile.entities.Gist
import io.reactivex.Observable
import retrofit2.http.*


interface GistsService {

    @GET("user/{username}/gists")
    fun getUserGists(@Path("username") userName: String): Observable<Gist>

    @GET("gists/public")
    fun getPublicGists(): Observable<List<Gist>>

    @GET("gists/starred")
    fun getStarredGists(): Observable<Gist>

    @GET("gists/{id}")
    fun getGist(@Path("id") id: String)

    @POST("gists")
    fun createGist(@Query("files") files: Any,
                   @Query("description") description: String,
                   @Query("public") public: Boolean)

    @PATCH("gists/{id}")
    fun changeGist(@Path("id") id: String,
                   @Query("description") description: String,
                   @Query("files") files: Any,
                   @Query("content") content: String,
                   @Query("filename") filename: String)

    @GET("gists/{id}/commits")
    fun getGistCommit(@Path("id") id: String)

    @GET("gists/{id}/comments")
    fun getGistComments(@Path("id") id: String)

    @PUT("gists/{id}/star")
    fun starGist(@Path("id") id: String)

    @DELETE("gists/{id}/star")
    fun unstarGist(@Path("id") id: String)

    @DELETE("gists/{id}")
    fun deleteGist(@Path("id") id: String)


}