package com.kovapss.gitmobile.model.api

import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.Comment
import io.reactivex.Single
import okhttp3.Response
import retrofit2.http.*


interface GistsService {

    @GET("users/{username}/gists")
    fun getUserGists(@Path("username") userName: String): Single<List<Gist>>

    @GET("gists/public")
    fun getPublicGists(): Single<List<Gist>>

    @GET("gists/starred")
    fun getStarredGists(@Query("access_token") accessToken : String): Single<List<Gist>>

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
    fun getGistComments(@Path("id") id: String) : Single<List<Comment>>

    @POST("gists/{id}/comments")
    fun createCommit(@Path("id") id : String,
                     @Query("body") commentBody : String)

    @GET("gists/{id}/star")
    fun checkGistStar(@Path("id") id : String,
                      @Query("access_token") accessToken : String) : Single<Response>

    @Headers("Content-Length: 0")
    @PUT("gists/{id}/star")
    fun starGist(@Path("id")  id: String,  @Query("access_token") accessToken : String)

    @DELETE("gists/{id}/star")
    fun unstarGist(@Path("id") id: String)

    @DELETE("gists/{id}")
    fun deleteGist(@Path("id") id: String)


}