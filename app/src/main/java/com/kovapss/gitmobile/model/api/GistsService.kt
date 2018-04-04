package com.kovapss.gitmobile.model.api

import com.kovapss.gitmobile.entities.Comment
import com.kovapss.gitmobile.entities.CreateGistData
import com.kovapss.gitmobile.entities.Gist
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface GistsService {

    @GET("users/{username}/gists")
    fun getUserGists(@Path("username") userName: String, @Query("page") page : Int): Single<Response<List<Gist>>>

    @GET("gists/public")
    fun getPublicGists(@Query("page") page : Int): Single<Response<List<Gist>>>

    @GET("gists/starred")
    fun getStarredGists(@Query("page") page : Int): Single<Response<List<Gist>>>

    @GET("gists/{id}")
    fun getGist(@Path("id") id: String) : Single<Response<Gist>>

    @POST("gists")
    @Headers("Accept: application/json")
    fun createGist(@Body gist : CreateGistData) : Single<Response<Gist>>

    @PATCH("gists/{id}")
    fun changeGist(@Path("id") id: String,
                   @Query("description") description: String,
                   @Query("files") files: Any,
                   @Query("content") content: String,
                   @Query("filename") filename: String)


    @GET("gists/{id}/commits")
    fun getGistCommit(@Path("id") id: String)

    @GET("gists/{id}/comments")
    fun getGistComments(@Path("id") id: String) : Single<retrofit2.Response<List<Comment>>>

    @POST("gists/{id}/comments")
    @Headers("Accept: application/vnd.github.VERSION.raw+json")
    fun createCommit(@Path("id") id : String,
                     @Query("body") commentBody : String)

    @GET("gists/{id}/star")
    fun checkGistStar(@Path("id") id : String) : Single<Response<ResponseBody>>

    @Headers("Content-Length: 0")
    @PUT("gists/{id}/star")
    fun starGist(@Path("id")  id: String) : Single<Response<ResponseBody>>

    @DELETE("gists/{id}/star")
    fun unstarGist(@Path("id") id: String) : Single<Response<ResponseBody>>

    @DELETE("gists/{id}")
    fun deleteGist(@Path("id") id: String) : Single<Response<ResponseBody>>


}